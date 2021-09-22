import React, { useState, useEffect } from "react";
import { ITeam } from "../../../models/team";
import { Col } from "react-bootstrap";
import { useDebouncedCallback } from "use-debounce";

import axios from "axios";

export interface ISetPointSpreadInputProps {
  team: ITeam;
}
export const SetPointSpreadInput: React.FC<ISetPointSpreadInputProps> = (
  props: ISetPointSpreadInputProps
) => {
  const [activePointSpread, setActivePointSpread] = useState<string>(
    props.team?.pointSpread?.toString()
  );

  const makeUpdateCall = useDebouncedCallback((newSeed: any) => {
    axios
      .post(`/api/admin/update-point-spread`, {
        teamId: props.team.teamId,
        newPointSpread: activePointSpread
      })
      .then(response => {
        console.log(response);
      })
      .catch(x => {
        console.log(x);
      });
  }, 800);

  const handleChange = (e: any) => {
    setActivePointSpread(e.target.value);
    makeUpdateCall(e.target.value);
  };

  return (
    <Col lg={4}>
      <input
        className="team-point-spread-input"
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
