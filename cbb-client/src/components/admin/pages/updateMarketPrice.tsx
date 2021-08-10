import React, { useEffect, useState } from "react";
import { Card } from "react-bootstrap";
import axios from "axios";
import { ITeam } from "../../../models/team";
import Loader from "react-loader-spinner";

export interface IUpdateMarketPriceProps {}

export const UpdateMarketPrice: React.FC<IUpdateMarketPriceProps> = (
  props: IUpdateMarketPriceProps
) => {
  //Populated from rest call on load
  const [allTeams, setAllTeams] = useState<ITeam[]>();

  //User selects from dropdown
  const [teamIdToUpdate, setTeamIdToUpdate] = useState<string>();

  //User selects from dropdown
  const [updatedPrice, setUpdatedPrice] = useState<string>();

  useEffect(() => {
    axios
      .get(`/api/admin/teams-playing-today`)
      .then(response => {
        console.log(response);
        setAllTeams(response.data);
      })
      .catch(error => {
        console.log(error);
      });
  }, []);

  const teamSelectOptions = (): JSX.Element => {
    if (allTeams == undefined)
      return (
        <div className="loading-wrapper">
          {" "}
          <Loader type="ThreeDots" color="#00BFFF" height={100} width={100} />
        </div>
      );

    const options = allTeams.map((team: ITeam, index: number) => {
      return (
        <option key={index} value={team.teamId}>
          {team.teamName}
        </option>
      );
    });

    return (
      <select onChange={e => setTeamIdToUpdate(e.target.value)}>
        <option selected disabled>
          Selct a team to update
        </option>
        {options}
      </select>
    );
  };

  const updateElements = (): JSX.Element | null => {
    if (teamIdToUpdate != undefined) {
      const team: ITeam = allTeams.find(
        x => x.teamId.toString() == teamIdToUpdate
      );

      console.log(team);

      const isSumbitBtnDisabled = (): boolean => {
        return (
          updatedPrice != undefined &&
          updatedPrice != null &&
          updatedPrice.length > 0
        );
      };

      return (
        <div className="update-elements-wrapper">
          <h4>{team.teamName}</h4>
          <div className="input-wrapper">
            <label htmlFor="currentPrice">Current Price:</label>
            <input
              name="currentPrice"
              value={team.currentMarketPrice}
              disabled
              maxLength={5}
              size={5}
            />
          </div>
          <div className="input-wrapper">
            <label htmlFor="nextPrice">Updated Price:</label>
            <input
              name="nextPrice"
              onChange={e => setUpdatedPrice(e.target.value)}
              value={updatedPrice}
              maxLength={5}
              size={5}
            />
          </div>
          <div>
            <button
              onClick={e =>
                alert(
                  `Make call to update ${teamIdToUpdate}'s price to ${updatedPrice}`
                )
              }
              disabled={!isSumbitBtnDisabled()}
            >
              Update Price
            </button>
          </div>
        </div>
      );
    }
    return null;
  };

  return (
    <Card id="set-market-price">
      <Card.Header>
        <h2>Update Market Price</h2>
      </Card.Header>
      <Card.Body>
        <div className="select-wrapper">{teamSelectOptions()}</div>
        <div>{updateElements()}</div>
      </Card.Body>
    </Card>
  );
};
