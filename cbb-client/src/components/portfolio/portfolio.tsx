import React, { useEffect, useState } from "react";
import { Row, Col, Card } from "react-bootstrap";
import { Link } from "react-router-dom";
import { PortfolioDetail } from "./components/portfolioDetail";
import { Page } from "../general/page";
import { InvestmentTable } from "./components/investmentTable";
import { Investment } from "./components/investment";
import { portfilioTip1, portfilioTip2 } from "../../data/staticMessages";
import { IInvestment } from "../../models/investment";
import axios from "axios";
import { IGamedata } from "../../models/gameData";
import { IUserGamedata } from "../../models/userGameData";
import Loader from "react-loader-spinner";
import { getDisplayTextCurrentRound } from "../../utilities/currentRoundUtility";
import { useHistory } from "react-router";
import { IMarketResponse } from "../../models/MarketResponse";
import { IMatchup } from "../../models/matchup";

import moment from "moment-timezone";

export interface IPortfolioProps {}

export const Portfolio: React.FC<IPortfolioProps> = (
  props: IPortfolioProps
) => {
  let history = useHistory();
  //TODO: cleanup first info card make sleeker
  //TODO: display upcoming games in card

  const [userInvestments, setUserInvestments] = useState<IInvestment[]>();
  const [gameData, setGameData] = useState<IGamedata>();
  const [userGameData, setUserGameGata] = useState<IUserGamedata>();

  //Upcoming games
  const [upcomingGames, setUpcomingGames] = useState<IMarketResponse>();

  useEffect(() => {
    axios
      .get("/api/user-investments")
      .then(response => {
        setUserInvestments(response.data);
      })
      .catch(error => {
        console.log(error);
        if (error.response.status == "403") history.push("/login");
      });
  }, []);

  useEffect(() => {
    axios
      .get("/api/cbb-game-data")
      .then(response => {
        setGameData(response.data);
      })
      .catch(error => {
        console.log(error);
      });
  }, []);

  useEffect(() => {
    axios
      .get("/api/user-game-data")
      .then(response => {
        setUserGameGata(response.data);
      })
      .catch(error => {
        console.log(error);
      });
  }, []);

  useEffect(() => {
    axios
      .get("/api/upcoming-games")
      .then(response => {
        setUpcomingGames(response.data);
      })
      .catch(error => {
        console.log("Error getting upcoming games");
      });
  }, []);

  const investments =
    userInvestments != null
      ? userInvestments.map((investment, index) => {
          return <Investment key={index} investment={investment} />;
        })
      : null;

  const grandTotalUserInvested: number =
    userInvestments != null
      ? userInvestments.reduce(
          (total, inv) => (total = total + inv.marketPrice),
          0
        )
      : 0;

  const investmentCardBody = (): JSX.Element => {
    if (userInvestments == undefined) {
      return (
        <div className="loading-wrapper">
          <Loader type="TailSpin" color="#00BFFF" height={100} width={100} />
        </div>
      );
    }
    if (userInvestments == null) {
      return (
        <Card.Body id="emptyPortfolioTips">
          <Card.Text id="main">
            Please visit the <Link to={"/market"}>Market</Link> to fill
            portfolio.
          </Card.Text>
          <Card.Text className="secondary">
            <b>
              <u>Tip:</u>
            </b>{" "}
            {portfilioTip1}
          </Card.Text>
          <Card.Text className="secondary">{portfilioTip2}</Card.Text>
        </Card.Body>
      );
    }
    return <InvestmentTable>{investments}</InvestmentTable>;
  };

  const getPortfolioData = (): JSX.Element => {
    if (gameData != null && userGameData != null) {
      return (
        <div className="portfolio-detail-wrapper">
          <PortfolioDetail heading="Networth">
            <span className="money-field">
              ${userGameData?.netWorth?.toLocaleString()}
            </span>
          </PortfolioDetail>
          <PortfolioDetail heading="Capital">
            <span className="money-field">
              ${userGameData?.cash?.toLocaleString()}
            </span>
          </PortfolioDetail>
          <PortfolioDetail heading="My leaderboard Pos">
            {userGameData?.leaderboardPosition.toLocaleString()}
          </PortfolioDetail>
          <PortfolioDetail heading="Transactions made">
            {userGameData?.userTransactionCount?.toLocaleString()}
          </PortfolioDetail>
          <PortfolioDetail heading="Leader's Networth">
            <span className="money-field">
              ${userGameData?.userTransactionCount?.toLocaleString()}
            </span>
          </PortfolioDetail>
          <PortfolioDetail heading="Total Money in play">
            <span className="money-field">
              ${gameData.totalMoneyInPlay.toLocaleString()}
            </span>
          </PortfolioDetail>
          <PortfolioDetail heading="Total Transactions">
            {gameData.totalTransactionsCount.toLocaleString()}
          </PortfolioDetail>
          <PortfolioDetail heading="Current Round">
            {getDisplayTextCurrentRound(gameData.currentRound)}
          </PortfolioDetail>
          <PortfolioDetail heading="Total Games left">
            {gameData.gamesLeft}
          </PortfolioDetail>
        </div>
      );
    }

    return (
      <div className="loading-wrapper">
        <Loader type="TailSpin" color="#00BFFF" height={100} width={100} />
      </div>
    );
  };

  const upComingGamesBody = (): JSX.Element => {
    if (upcomingGames == undefined)
      return (
        <div className="loading-wrapper">
          <Loader type="TailSpin" color="#00BFFF" height={100} width={100} />
        </div>
      );

    if (upcomingGames?.matchups.length < 1)
      return (
        <Card.Body>
          <h6 id="no-more-games">No games remaining today</h6>
        </Card.Body>
      );

    const games = upcomingGames.matchups.map(
      (matchup: IMatchup, index: number) => {
        const startTimeParsed = moment(matchup.startTime, ["h:mm a"]).tz(
          "America/Chicago"
        );
        const startTimeLocalTimeZone: string = startTimeParsed
          .tz(
            new window.Intl.DateTimeFormat().resolvedOptions().timeZone //gets timezone from users browser
          )
          .format("h:mm a");

        return (
          <div key={index} className="upcoming-game">
            <span>
              <img src={"/img/teams/" + matchup.team1.teamName + ".png"} />
              {matchup.team1.teamName}
            </span>
            <span className="startTime">({startTimeLocalTimeZone})</span>
            <span>
              {matchup.team2.teamName}
              <img
                loading="lazy"
                src={"/img/teams/" + matchup.team2.teamName + ".png"}
              />
            </span>
          </div>
        );
      }
    );

    return <Card.Body className="upcoming-games-card">{games}</Card.Body>;
  };

  return (
    <Page pageId="portfolio-wrapper">
      <Row className="game-data">
        <Col lg={6}>
          <Card className="card-details">
            <Card.Header>
              <Card.Title className="portfolio-header-title">
                <i className="fa fa-folder" />
                {userGameData?.fullName}
              </Card.Title>
            </Card.Header>
            <Card.Body>
              <Row noGutters={true}>
                <Col>{getPortfolioData()}</Col>
              </Row>
            </Card.Body>
          </Card>
        </Col>
        <Col lg={6}>
          <Card className="card-details">
            <Card.Header>
              <Card.Title className="portfolio-header-title">
                <i className="fa fa-clock"></i>Upcoming Games
              </Card.Title>
            </Card.Header>
            {upComingGamesBody()}
          </Card>
        </Col>
      </Row>
      <Row>
        <Col xl={8}>
          <Card className="portfolio-investments">
            <Card.Header>
              <Card.Title className="portfolio-header-title">
                <i className="fa fa-money-bill-alt"></i>My Investments
                <span id="investmentTotalAmt">
                  (${grandTotalUserInvested.toLocaleString()})
                </span>
              </Card.Title>
            </Card.Header>
            {investmentCardBody()}
          </Card>
        </Col>
      </Row>
    </Page>
  );
};
