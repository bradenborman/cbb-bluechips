import React, { useState, useEffect } from "react";
import { Card } from "react-bootstrap";
import axios from "axios";
import Loader from "react-loader-spinner";

export interface IGameSettingsProps {}

export const GameSettings: React.FC<IGameSettingsProps> = (
  props: IGameSettingsProps
) => {
  const [currentRoundState, setCurrentRoundState] = useState<string>();
  const [signUpAllowed, setSignUpAllowed] = useState<boolean>();

  useEffect(() => {
    axios
      .get("/api/admin/game-settings")
      .then(response => {
        console.log(response);
        setCurrentRoundState(response.data.currentRound);
        setSignUpAllowed(response.data.signUpAllowed);
      })
      .catch(error => {
        console.log(error);
      });
  }, []);

  const handleRoundUpdateSubmit = (e: any) => {
    e.preventDefault();
    axios
      .post(`/api/admin/update-current-round?round=${currentRoundState}`)
      .then(response => {
        console.log(response);
      })
      .catch(error => {
        console.log(error);
      });
  };

  const gameSettings = (): JSX.Element => {
    if (currentRoundState == null || currentRoundState == undefined) {
      return (
        <div className="loading-wrapper">
          <Loader type="TailSpin" color="#00BFFF" height={100} width={100} />
        </div>
      );
    } else {
      const getRoundOptions = () => {
        return ["64", "32", "16", "8", "4", "2"].map(round => {
          return (
            <span>
              <input
                type="radio"
                onChange={e => setCurrentRoundState(e.target.value)}
                checked={round == currentRoundState}
                name="updatedRound"
                key={round}
                value={round}
                required
              />
              {round}
            </span>
          );
        });
      };

      return (
        <div>
          <form onSubmit={handleRoundUpdateSubmit} id="current-round-form">
            <label>Current Round</label>
            <div id="current-round-radio-wrapper">{getRoundOptions()}</div>
            <button type="submit">Submit</button>
          </form>

          <form id="sign-up-allowed-form">
            <input
              type="checkbox"
              name="updatedSignUpAllowed"
              checked={signUpAllowed}
              onChange={e => setSignUpAllowed(!signUpAllowed)}
            />
            <label>Sign Up Allowed</label>
            <button type="submit">Submit</button>
          </form>

          <form id="delete-players-form">
            <button disabled type="submit">
              Delete all players..
            </button>
          </form>
          <form id="reset-game-form">
            <button disabled type="submit">
              Reset Game..
            </button>
          </form>
        </div>
      );
    }
  };

  return (
    <Card id="game-settings-wrapper">
      <Card.Header>
        <h2>Game Settings</h2>
      </Card.Header>
      <Card.Body>{gameSettings()}</Card.Body>
    </Card>
  );
};
