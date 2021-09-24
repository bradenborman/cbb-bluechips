import React, { useState, useEffect } from "react";

import { useParams } from "react-router";
import { Row, Col, Card } from "react-bootstrap";
import { Page } from "../general/page";
import {
  TransactionSlider,
  TransactionAction
} from "./components/transactionSlider";
import { TransactionType } from "./components/transactionSlider";

import axios from "axios";
import { ITeamExchangeDetailsResponse } from "../../models/teamExchangeDetailsResponse";
import Loader from "react-loader-spinner";

export interface ITradeProps {}
export const Trade: React.FC<ITradeProps> = (props: ITradeProps) => {
  let { teamId } = useParams();

  const [
    teamExchangeDetailsResponse,
    setTeamExchangeDetailsResponse
  ] = useState<ITeamExchangeDetailsResponse>();
  const [transactionAction, setTransactionAction] = useState<TransactionAction>(
    { currentTransactionCost: "$0", lastTransactionType: null }
  );

  useEffect(() => {
    axios
      .get(`/api/exchange-details/${teamId}`)
      .then(response => {
        setTeamExchangeDetailsResponse(response.data);
        console.log(response.data);
      })
      .catch(error => {
        console.log(error);
      });
  }, []);

  //TODO: show capital available (/)
  //TODO: show team data: name, current price, point spread
  //TODO: show text on screen that says what team would have to win buy to make any money if favored or would have to not lose by if underdog
  //TODO: show countdown timer to when team locks
  //TODO: option to place order in as team is locked: (go ahead and purcahse, but override insert timestamp to start of game so it doesnt appear until game starts -> change transactions to only show current time and before)

  const tradeScreen = (): JSX.Element => {
    if (teamExchangeDetailsResponse == undefined) {
      return (
        <Card>
          <Card.Body>
            <div className="loading-wrapper">
              <Loader
                type="TailSpin"
                color="#00BFFF"
                height={100}
                width={100}
              />
            </div>
          </Card.Body>
        </Card>
      );
    }

    const topHolders = teamExchangeDetailsResponse.topHolders.map(
      (holder: string, index: number) => {
        return <li key={index}>{holder}</li>;
      }
    );

    return (
      <Card>
        <Card.Header>
          <h2>{teamExchangeDetailsResponse?.teamName}</h2>
          <img
            className="logo"
            src={`/img/teams/${teamExchangeDetailsResponse.teamName}.png`}
          />
        </Card.Header>
        <Card.Body>
          <Row>
            <div id="trade-info">
              <Col md={12} lg={12}>
                <h6>
                  Capital Available: $
                  {teamExchangeDetailsResponse.purchasingPower.toLocaleString()}
                </h6>
              </Col>
              <Col md={12} lg={12}>
                <h6>
                  Current Market Price: $
                  {teamExchangeDetailsResponse.currentMarketPrice.toLocaleString()}
                </h6>
              </Col>
              <hr />
              <Col md={12} lg={12}>
                <h6>
                  <i className="fa fa-shopping-cart" /> Transaction Cost:{" "}
                  {transactionAction.currentTransactionCost}
                </h6>
              </Col>
            </div>
          </Row>
          <Row>
            <Col md={6} lg={4}>
              <TransactionSlider
                transactionType={TransactionType.BUY}
                max={teamExchangeDetailsResponse.maximumCanPurchase}
                teamId={teamExchangeDetailsResponse.teamId}
                currentPrice={teamExchangeDetailsResponse.currentMarketPrice}
                _setTransactionAction={setTransactionAction}
                zeroValue={
                  transactionAction.lastTransactionType == TransactionType.SELL
                }
              />
            </Col>
            <Col md={6} lg={4}>
              <TransactionSlider
                transactionType={TransactionType.SELL}
                max={teamExchangeDetailsResponse.amountSharesOwned}
                teamId={teamExchangeDetailsResponse.teamId}
                currentPrice={teamExchangeDetailsResponse.currentMarketPrice}
                _setTransactionAction={setTransactionAction}
                zeroValue={
                  transactionAction.lastTransactionType == TransactionType.BUY
                }
              />
            </Col>
            <Col md={12} lg={4}>
              <h6>Top Holders:</h6>
              <ul>{topHolders}</ul>
            </Col>
          </Row>
        </Card.Body>
      </Card>
    );
  };

  return (
    <Page pageId="trade-wrapper">
      <Row>
        <Col lg={12}>{tradeScreen()}</Col>
      </Row>
    </Page>
  );
};
