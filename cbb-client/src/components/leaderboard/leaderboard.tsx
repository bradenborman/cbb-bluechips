import React, { useState, useEffect } from "react";
import { Page } from "../general/page";
import { Row, Col, Card } from "react-bootstrap";
import { priorWinners } from "../../data/game-data";
import { LeaderBoardTable } from "./components/leaderboardTable";
import { ILeaderBoardUser } from "../../models/leaderboardUser";
import Loader from "react-loader-spinner";

import axios from "axios";
import { DonationCard } from "./components/donationCard";

export interface ILeaderboardProps {
  paypalDonationAmount: number;
}

/*
https://github.com/Luehang/react-paypal-button-v2
*/

export const Leaderboard: React.FC<ILeaderboardProps> = (
  props: ILeaderboardProps
) => {
  const [leaderboardData, setLeaderboardData] = useState<ILeaderBoardUser[]>();

  useEffect(() => {
    axios
      .get("/api/leaderboard")
      .then(response => {
        setLeaderboardData(response.data);
      })
      .catch(error => {
        console.log(error);
      });
  }, []);

  const pastWinners = priorWinners.map((user, index) => {
    return <li key={index}>{user}</li>;
  });

  const buildLeaderboard = (): JSX.Element => {
    if (leaderboardData != null && leaderboardData != undefined)
      return <LeaderBoardTable leaders={leaderboardData} />;
    return (
      <div className="loading-wrapper">
        <Loader type="TailSpin" color="#00BFFF" />
      </div>
    );
  };

  return (
    <Page pageId="leaderboard-wrapper">
      <Row>
        <Col lg={8}>
          <Card id="leaderboardCard">{buildLeaderboard()}</Card>
        </Col>
        <Col lg={4}>
          <Card id="previousWinnersCard">
            <Card.Body>
              <h4>
                <i className="fa fa-trophy"></i>Previous Winners:
              </h4>
              <ul>{pastWinners}</ul>
            </Card.Body>
          </Card>
          <DonationCard
            clientId={
              "AbiTp3MyLJCqlqUhgWWDpFC-M8VRE6bHA1GHs40nCam1UpxkHRpFRlkbP_SpwAUGguGKojDw-6a8sWtd"
            }
            paypalDonationAmount={props.paypalDonationAmount}
            hasAlreadyDonated={false}
          />
        </Col>
      </Row>
    </Page>
  );
};
