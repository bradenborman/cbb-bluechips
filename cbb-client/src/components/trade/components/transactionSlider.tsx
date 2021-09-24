import React, { useState, useEffect } from "react";
import { TransactionAction } from "../../../models/TransactionAction";
import axios from "axios";

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
  const btnTxt = props.transactionType;

  const [sliderValue, setSliderValue] = useState<number>(0);

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
      confirm(`Would you like to ${props.transactionType} shares of this team?`)
    ) {
      // alert(`Should make call to ${props.transactionType} teamid: ${props.teamId} for ${sliderValue} shares`)
      axios
        .post(
          `/api/trade-action/${props.transactionType}?teamId=${props.teamId}&volume=${sliderValue}`
        )
        .then(response => {
          console.log(response.data);
        })
        .catch(error => {
          console.log(error);
        });
    }
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
      <button
        disabled={sliderValue < 1}
        id="buyBTN"
        className="sell btn btn-success tradeBTN"
        onClick={handleButtonClick}
      >
        {btnTxt} {sliderValue > 0 ? sliderValue : null}
      </button>
    </div>
  );
};
