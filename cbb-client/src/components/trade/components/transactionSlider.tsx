import React, { useState } from "react";

export enum TransactionType {
  BUY = "Buy",
  SELL = "Sell"
}

export interface ITransactionSliderProps {
  transactionType: TransactionType;
  min?: number;
  max: number;
}
export const TransactionSlider: React.FC<ITransactionSliderProps> = (
  props: ITransactionSliderProps
) => {
  const btnTxt = props.transactionType;

  const [sliderValue, setSliderValue] = useState<number>(0);

  const handleChange = (e: any) => {
    setSliderValue(e.target.value);
  };

  return (
    <div className="text-center input-wrapper">
      <input
        className="slider"
        type="range"
        min={props.min != null ? props.min : 0}
        max={props.max}
        value={sliderValue}
        id="buySlider"
        name="volume"
        onChange={handleChange}
      />
      <button
        disabled={sliderValue < 1}
        id="buyBTN"
        className="sell btn btn-success tradeBTN"
      >
        {btnTxt} {sliderValue > 0 ? sliderValue : null}
      </button>
    </div>
  );
};
