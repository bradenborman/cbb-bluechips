import React from "react";
import { Page } from "../general/page";
import { Row, Col, Tab, ListGroup } from "react-bootstrap";
import { GameSettings } from "./pages/gameSettings";
import { PointSpreads } from "./pages/pointSpreads";
import { UpdateMarketPrice } from "./pages/updateMarketPrice";
import { UserSettings } from "./pages/userSettings";
import { UpdateSeeds } from "./pages/updateSeeds";

export interface IAdminProps {}

export const Admin: React.FC<IAdminProps> = (props: IAdminProps) => {
  return (
    <Page pageId="admin-wrapper">
      <Tab.Container id="admin-menu" defaultActiveKey="#update-price">
        <Row>
          <Col lg={3}>
            <ListGroup>
              <ListGroup.Item action href="#game-settings">
                Game Settings
              </ListGroup.Item>
              <ListGroup.Item action href="#update-seeds">
                Update Seeds
              </ListGroup.Item>
              <ListGroup.Item action href="#set-point-spreads">
                Set Point Spreads
              </ListGroup.Item>
              <ListGroup.Item action href="#update-price">
                Update Market Price
              </ListGroup.Item>
              <ListGroup.Item action href="#user-settings">
                User Settings
              </ListGroup.Item>
            </ListGroup>
          </Col>
          <Col lg={9}>
            <Tab.Content>
              <Tab.Pane eventKey="#game-settings">
                <GameSettings />
              </Tab.Pane>
              <Tab.Pane eventKey="#update-seeds">
                <UpdateSeeds />
              </Tab.Pane>
              <Tab.Pane eventKey="#set-point-spreads">
                <PointSpreads />
              </Tab.Pane>
              <Tab.Pane eventKey="#update-price">
                <UpdateMarketPrice />
              </Tab.Pane>
              <Tab.Pane eventKey="#user-settings">
                <UserSettings />
              </Tab.Pane>
            </Tab.Content>
          </Col>
        </Row>
      </Tab.Container>
    </Page>
  );
};
