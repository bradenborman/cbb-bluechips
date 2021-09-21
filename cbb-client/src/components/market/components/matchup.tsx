import React, { useState, useEffect } from "react";
import { Row, Col, Card } from "react-bootstrap";
import { TeamCard } from "./teamcard";
import { IMatchup } from "../../../models/matchup";
import classNames from "classnames";

import moment from "moment";
import { ITeam } from "../../../models/team";

export interface IMatchupProps {
  matchup: IMatchup;
}

export const Matchup: React.FC<IMatchupProps> = (props: IMatchupProps) => {
  //USED DOWNSTEAM SO THAT UI AUTO LOCKS WHEN TIMER IS DONE -- back end locks separately
  const [team1, setTeam1] = useState<ITeam>(props.matchup.team1);
  const [team2, setTeam2] = useState<ITeam>(props.matchup.team2);

  const [collapsed, setCollapsed] = useState<boolean>(true);
  const [countdownUntilLock, setCountdownUntilLock] = useState<string>(
    props.matchup.startTime
  );

  console.log(props.matchup.startTime);

  const startTimeParsed = moment(props.matchup.startTime, ["h:mm a"]);
  const loadedInAfterStartTime = moment().isAfter(startTimeParsed);

  useEffect(() => {
    if (loadedInAfterStartTime) {
      setCountdownUntilLock("Game already tipped");
    } else {
      //Before loaded in starttime

      var interval = setInterval(() => {
        const rightNow = moment();
        if (
          startTimeParsed.diff(rightNow, "minutes") < 15 &&
          rightNow.isBefore(startTimeParsed)
        ) {
          //GAME STARTS IN 15 mins or less
          let totalSeconds: number = startTimeParsed.diff(
            rightNow,
            "seconds",
            true
          );
          const mins = Math.floor(totalSeconds / 60);
          totalSeconds = Math.floor(totalSeconds - mins * 60);
          setCountdownUntilLock(
            "Starts in: " +
              mins +
              ":" +
              (totalSeconds < 10 ? "0" : "") +
              totalSeconds.toString()
          );
        } else if (rightNow.isAfter(startTimeParsed)) {
          //PAST START TIME
          props;
          clearInterval(interval);
          const lockedTeam1 = team1,
            lockedTeam2 = team2;
          lockedTeam1.locked = true;
          lockedTeam2.locked = true;
          setTeam1(lockedTeam1);
          setTeam2(lockedTeam2);
          setCountdownUntilLock("Game in progess");
        } else if (
          startTimeParsed.diff(rightNow, "minutes") >= 60 * 1.5 &&
          rightNow.isBefore(startTimeParsed)
        ) {
          //GAME STARTS IN LATER => hr and a half -> remove interval on this one mins or less
          clearInterval(interval);
        }
      }, 1000);
    }
  }, []);

  const matchupPreview = (): JSX.Element => {
    return (
      <div className="team-name-matchup-wrapper">
        <div className="team-name-with-img">
          <img
            className="team-icon"
            src={"/img/teams/" + props.matchup.team1.teamName + ".png"}
            loading="lazy"
          />
          {props.matchup.team1.teamName}
        </div>
        <div className="vs">vs</div>
        <div className="team-name-with-img">
          {props.matchup.team2.teamName}
          <img
            className="team-icon"
            src={"/img/teams/" + props.matchup.team2.teamName + ".png"}
            loading="lazy"
          />
        </div>
      </div>
    );
  };

  const getClockIcon = (): JSX.Element => {
    if (props.matchup.startTime == countdownUntilLock)
      return (
        <span className="start-time">
          <i className="fas fa-clock" /> {countdownUntilLock}
        </span>
      );
    else return <span className="start-time">{countdownUntilLock}</span>;
  };

  return (
    <Row className="match-up">
      <Col>
        <Card className="match-up-card">
          <Card.Header className="match-up-header">
            {matchupPreview()}
            {getClockIcon()}
          </Card.Header>
          <Card.Body
            className={classNames("match-up-body", { collapsed: collapsed })}
          >
            <Row>
              <Col lg={6}>
                <TeamCard team={team1} />
              </Col>
              <Col lg={6}>
                <TeamCard team={team2} />
              </Col>
            </Row>
          </Card.Body>
          <div
            onClick={e => setCollapsed(!collapsed)}
            className="match-up-expander-wrapper"
          >
            <i
              className={classNames("fa fa-caret-down", {
                collapsed: collapsed
              })}
            />
          </div>
        </Card>
      </Col>
    </Row>
  );
};
