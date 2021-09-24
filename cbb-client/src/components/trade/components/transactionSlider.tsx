import React, { useState, useEffect } from "react";
import { TransactionAction } from "../../../models/TransactionAction";
import axios from "axios";
import Loader from "react-loader-spinner";

export enum TransactionType {
  BUY = "Buy",
  SELL = "Sell"
}

export interface ITransactionSliderProps {
  transactionType: TransactionType;
  max: number;
  teamId: string;
  currentPrice: number;
  _setTransactionAction: (x: TransactionAction) => void;
  zeroValue: boolean;
}
export const TransactionSlider: React.FC<ITransactionSliderProps> = (
  props: ITransactionSliderProps
) => {
  const [giveWarning, setGiveWarning] = useState<boolean>(false);

  const [sliderValue, setSliderValue] = useState<number>(0);
  const [makingRestCall, setMakingRestCall] = useState<boolean>(false);

  const handleChange = (e: any) => {
    setSliderValue(e.target.value);
    let modifier: string =
      props.transactionType == TransactionType.BUY ? "$" : "-$";
    let newPrice: string = `${modifier}${(
      props.currentPrice * e.target.value
    ).toLocaleString()}`;

    if (e.target.value == 0) newPrice = newPrice.replace("-", "");

    props._setTransactionAction({
      currentTransactionCost: newPrice,
      lastTransactionType: props.transactionType
    });
  };

  const handleButtonClick = () => {
    if (
      giveWarning &&
      confirm(`Would you like to ${props.transactionType} shares of this team?`)
    ) {
      // alert(`Should make call to ${props.transactionType} teamid: ${props.teamId} for ${sliderValue} shares`)
      setMakingRestCall(true);
      axios
        .post(
          `/api/trade-action/${props.transactionType}?teamId=${props.teamId}&volume=${sliderValue}`
        )
        .then(response => {
          setMakingRestCall(false);
          window.location.reload();
        })
        .catch(error => {
          setMakingRestCall(false);
          console.log(error);
        });
    }
  };

  const button = (): JSX.Element => {
    const btnTxt = props.transactionType;

    if (!makingRestCall)
      return (
        <button
          disabled={sliderValue < 1 || props.zeroValue}
          id="buyBTN"
          className="sell btn btn-success tradeBTN"
          onClick={handleButtonClick}
        >
          {btnTxt} {sliderValue > 0 && !props.zeroValue ? sliderValue : null}
        </button>
      );

    return (
      <button
        disabled={true}
        id="buyBTN"
        className="sell btn btn-success tradeBTN"
      >
        <Loader type="ThreeDots" color="white" height={30} width={30} />
      </button>
    );
  };

  return (
    <div className="text-center input-wrapper">
      <input
        className="slider"
        type="range"
        max={props.max}
        value={props.zeroValue ? 0 : sliderValue}
        id="buySlider"
        name="volume"
        onChange={handleChange}
      />
      {button()}
    </div>
  );
};
