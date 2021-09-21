import React, { useEffect, useState } from "react";
import { Matchup } from "./components/matchup";
import { Page } from "../general/page";
import axios from "axios";
import { IMarketResponse } from "../../models/MarketResponse";
import { useHistory } from "react-router";
import { IMatchup } from "../../models/matchup";
import Loader from "react-loader-spinner";
export interface IMarketProps {}

export const Market: React.FC<IMarketProps> = (props: IMarketProps) => {
  let history = useHistory();

  const [marketResponse, setMarketResponse] = useState<IMarketResponse>();

  useEffect(() => {
    axios
      .get("/api/market")
      .then(response => {
        setMarketResponse(response.data);
        console.log(response.data);
      })
      .catch(error => {
        console.log(error);
        if (error.response.status == "403") history.push("/login");
      });
  }, []);

  const mapMatchups = (): JSX.Element => {
    if (marketResponse == undefined) {
      return (
        <div className="loading-wrapper">
          <Loader type="Circles" color="#00BFFF" height={115} width={115} />
        </div>
      );
    }
    if (marketResponse != undefined) {
      if (marketResponse.matchups.length > 0) {
        const matchups = marketResponse.matchups.map(
          (match: IMatchup, index: number) => (
            <Matchup matchup={match} key={index} />
          )
        );
        return <div id="all-matchups-wrapper">{matchups}</div>;
      }
      return (
        <div id="all-matchups-wrapper">
          <div className="no-games-message">
            <h2>No Games today</h2>
            <p>
              Please return on a day there is a game scheduled.
              <br /> Point spreads are set on game day as well. Best not to buy
              when the spreads are not set ;)
            </p>
          </div>
        </div>
      );
    }
  };

  return <Page pageId="market-wrapper">{mapMatchups()}</Page>;
};
