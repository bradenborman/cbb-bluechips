import React, { useState } from "react";
import { Card } from "react-bootstrap";

import { PayPalButton } from "react-paypal-button-v2";
import axios from "axios";

export interface IDonationCard {
  paypalDonationAmount: number;
  hasAlreadyDonated: boolean;
  clientId: string;
}

export const DonationCard: React.FC<IDonationCard> = (props: IDonationCard) => {
  const [sumbitSuccessful, setSumbitSuccessful] = useState<Boolean>(false);

  const updatePaymentSuccessful = (details: any, data: any) => {
    axios
      .post("/api/paypal-transaction-complete", {
        orderId: data.orderID,
        playGivenName: details.payer.name.given_name,
        buyerEmail: details.payer.name.given_name,
        purchaseUnits: details.purchase_units
      })
      .then(response => {
        setSumbitSuccessful(true);
      })
      .catch(error => {
        console.log(error);
      });
  };

  const cardBodySumbitSuccessful = (): JSX.Element => {
    return (
      <Card.Body>
        <Card.Text>Thank you for playing.</Card.Text>
        <Card.Text className="text-center">
          A <span className="green">$50,000</span> bonus will be credited with a
          donation.
        </Card.Text>
      </Card.Body>
    );
  };

  const cardBodyAlreadyDonated = (): JSX.Element => {
    return (
      <Card.Body>
        <Card.Text>Thank you for playing.</Card.Text>
        <Card.Text className="text-center">
          You have already donated and been awarded the{" "}
          <span className="green">$50,000</span> thank you.{" "}
        </Card.Text>
      </Card.Body>
    );
  };

  const getCardBody = (): JSX.Element => {
    if (props.hasAlreadyDonated) return cardBodyAlreadyDonated();
    if (sumbitSuccessful) return cardBodySumbitSuccessful();
    return (
      <Card.Body>
        <Card.Text>Thank you for playing.</Card.Text>
        <Card.Text className="text-center">
          A <span className="green">$50,000</span> in-game thank you will be
          applied with a donation.{" "}
        </Card.Text>
        <PayPalButton
          amount="0.01"
          currency={"USD"}
          shippingPreference={"NO_SHIPPING"}
          onSuccess={(details: any, data: any) => {
            return updatePaymentSuccessful(details, data);
          }}
          catchError={(err: any) => {
            alert("Un-able to fufill donation");
          }}
          options={{
            clientId: props.clientId,
            disableFunding: "credit"
          }}
        />
      </Card.Body>
    );
  };

  return <Card id="donation-card">{getCardBody()}</Card>;
};
