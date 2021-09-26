import React from "react";
import { useHistory } from "react-router";
import { IInvestment } from "../../../models/investment";
import classNames from "classnames";

export interface IInvestmentProps {
  investment: IInvestment;
}

export const Investment: React.FC<IInvestmentProps> = (
  props: IInvestmentProps
) => {
  let history = useHistory();

  const handleTradeClick = (e: any) => {
    history.push(`/trade/${props.investment.teamId}`);
  };

  return (
    <tr>
      <td className="logoTd">
        <img
          id={props.investment.teamId}
          className="logo"
          src={`/img/teams/${props.investment.teamName}.png`}
          onClick={handleTradeClick}
        />
        <span
          className={classNames(
            "pointSpread",
            Number.parseInt(props.investment.nextPointSpread) > 0
              ? "underdog"
              : "favorite"
          )}
        >
          {props.investment.nextPointSpread}
        </span>
      </td>
      <td>
        <span className="hideSmall">
          <span>{props.investment.teamName}</span>
        </span>
      </td>
      <td>{props.investment.amountOwned}</td>
      <td>${props.investment.marketPrice.toLocaleString()}</td>
      <td>
        $
        {(
          props.investment.marketPrice * props.investment.amountOwned
        ).toLocaleString()}
      </td>
      <td className="hideSmall">
        <button
          type="button"
          className="tradeBTN btn btn-primary"
          onClick={handleTradeClick}
        >
          Trade
        </button>
      </td>
    </tr>
  );
};
