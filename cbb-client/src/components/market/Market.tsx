import React, { useEffect, useState } from "react";
import { Matchup } from "./components/matchup";
import { matchup1, matchup2, matchup3, matchup4 } from "../../data/test-data";
import { Page } from "../general/page";
import axios from "axios";
import { IMarketResponse } from "../../models/MarketResponse";
import { useHistory } from "react-router";
import { IMatchup } from "../../models/matchup";
export interface IMarketProps {}

export const Market: React.FC<IMarketProps> = (props: IMarketProps) => {
  let history = useHistory();

  const [marketResponse, setMarketResponse] = useState<IMarketResponse>();

  useEffect(() => {
    axios
      .get("/api/market")
      .then(response => {
        setMarketResponse(response.data);
      })
      .catch(error => {
        console.log(error);
        if (error.response.status == "403") history.push("/login");
      });
  }, []);

  const mapMatchups = (): JSX.Element => {
    if (marketResponse != undefined) {
      const matchups = marketResponse.matchups.map(
        (match: IMatchup, index: number) => (
          <Matchup matchup={match} key={index} />
        )
      );
      return <div id="all-matchups-wrapper">{matchups}</div>;
    }
    return <div id="all-matchups-wrapper"></div>;
  };

  return <Page pageId="market-wrapper">{mapMatchups()}</Page>;
};
