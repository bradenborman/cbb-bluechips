import React from "react";
import { Card, Button } from "react-bootstrap";
import { ITeam } from "../../../models/team";
import classNames from "classnames";
import Chart from "react-google-charts";
import Loader from "react-loader-spinner";
import { useHistory } from "react-router";
import useWindowDimensions from "../../../utilities/windowDimensions";

export interface ITeamCardProps {
  team: ITeam;
}

export const TeamCard: React.FC<ITeamCardProps> = (props: ITeamCardProps) => {
  let history = useHistory();
  const { height, width } = useWindowDimensions();

  const tradeTxt = props.team.locked ? (
    <span>
      <i className="fa fa-lock" /> Locked
    </span>
  ) : (
    <Button
      onClick={e => {
        handleTradeClick(e);
      }}
      variant="outline-primary"
    >
      Trade
    </Button>
  );

  const data: any = [["Round", "Price"]];

  if (props.team.priceHistory.length == 0) data.push(["64", 5000]);

  props.team.priceHistory?.forEach(priceHistory => {
    const x = priceHistory.split(":");
    data.push([x[0], Number.parseInt(x[1])]);
  });

  const graphOptions = {
    curveType: "function",
    legend: { position: "bottom" }
  };

  const loader = (
    <Loader type="ThreeDots" color="#00BFFF" height={200} width={100} />
  );

  const handleTradeClick = (e: any) => {
    history.push("/trade/" + props.team.teamId);
  };

  return (
    <Card className="team-card">
      <Card.Title className="team-main-details">
        <span className="team-name">{props.team.teamName}</span>
        <Card.Text className="current-market-price">
          ${props.team?.currentMarketPrice.toLocaleString()}
        </Card.Text>
      </Card.Title>
      <Card.Body>
        <Card.Text className="point-spread-bar">
          {props.team.pointSpread
            ? props.team?.pointSpread > 0
              ? "underdog"
              : "favorite"
            : ""}
          <small
            className={classNames(
              { underdog: props.team.pointSpread > 0 },
              { favorite: props.team.pointSpread < 0 }
            )}
          >
            {props.team.pointSpread
              ? props.team?.pointSpread > 0
                ? ` (${props.team?.pointSpread})`
                : ` (${props.team?.pointSpread})`
              : "Not Set"}
          </small>
        </Card.Text>
        <Chart
          chartType="LineChart"
          width="100%"
          className="chart"
          height={width > 390 ? "175px" : "100px"}
          data={data}
          loader={loader}
          options={graphOptions}
        />
        <Card.Text className="trade-btn-wrapper">{tradeTxt}</Card.Text>
      </Card.Body>
      <Card.Footer>
        <Card.Text as={"span"} className="seed-info">
          Seed: {props.team.seed}
        </Card.Text>
        <Card.Text as={"span"} className="shares-outstanding">
          Shares OutStanding: {props.team.sharesOutstanding}
        </Card.Text>
      </Card.Footer>
    </Card>
  );
};
