import React, { useState, useEffect } from "react";
import { ITeam } from "../../../models/team";
import { Col } from "react-bootstrap";
import { useDebouncedCallback } from "use-debounce";

import axios from "axios";
import classNames from "classnames";

export interface ISetPointSpreadInputProps {
  team: ITeam;
}

enum UpdateStatus {
  Loaded = "L",
  Updating = "U",
  Success = "S",
  Failure = "F"
}

export const SetPointSpreadInput: React.FC<ISetPointSpreadInputProps> = (
  props: ISetPointSpreadInputProps
) => {
  const [activePointSpread, setActivePointSpread] = useState<string>(
    props.team?.pointSpread?.toString()
  );

  const [updatedStatus, setUpdatedStatus] = useState<UpdateStatus>(
    UpdateStatus.Loaded
  );

  const makeUpdateCall = useDebouncedCallback((newSeed: any) => {
    setUpdatedStatus(UpdateStatus.Updating);
    axios
      .post(`/api/admin/update-point-spread`, {
        teamId: props.team.teamId,
        newPointSpread: activePointSpread
      })
      .then(response => {
        setUpdatedStatus(UpdateStatus.Success);
        console.log(response);
      })
      .catch(x => {
        setUpdatedStatus(UpdateStatus.Failure);
        console.log(x);
      });
  }, 800);

  const handleChange = (e: any) => {
    setActivePointSpread(e.target.value);
    makeUpdateCall(e.target.value);
  };

  const status: string = updatedStatus;

  return (
    <Col lg={4}>
      <input
        className={classNames("team-point-spread-input", status)}
        maxLength={2}
        type="number"
        step={0.5}
        onChange={handleChange}
        value={activePointSpread}
      />
      {props.team.teamName}
    </Col>
  );
};
