import React from "react";
import { Card } from "react-bootstrap";

export interface IMatchupMakerProps {}

export const UpdateMarketPrice: React.FC<IMatchupMakerProps> = (
  props: IMatchupMakerProps
) => {
  return (
    <Card>
      <Card.Header>
        <h2>Update Market Price</h2>
      </Card.Header>
    </Card>
  );
};
