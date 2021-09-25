import React, { useState } from "react";
import { Row, Col, Button } from "react-bootstrap";
import { validateEmail } from "../../../utilities/signupValidation";
import axios from "axios";
import Loader from "react-loader-spinner";

export interface ISignUpProps {}
export const SignUp: React.FC<ISignUpProps> = (props: ISignUpProps) => {
  const [firstName, setFirstName] = useState<string>(""),
    [lastName, setLastName] = useState<string>(""),
    [emailAddress, setEmailAddress] = useState<string>(""),
    [password, setPassword] = useState<string>("");

  const [submitting, setSubmitting] = useState<boolean>(false);

  const handleSubmit = (e: any) => {
    e.preventDefault();
    let isValid: boolean = true;
    isValid = validateEmail(emailAddress);

    if (isValid) {
      setSubmitting(true);
      axios
        .post("/api/create-user", {
          firstName,
          lastName,
          email: emailAddress,
          password
        })
        .then(response => {
          window.location.href = `/login?email=${emailAddress}`;
        })
        .catch(error => {
          console.log(error);
          setSubmitting(false);
          alert(error.response.data);
        });
    }
  };

  const getButtonText = () => {
    if (submitting)
      return (
        <React.Fragment>
          <Loader type="ThreeDots" color="white" height={30} width={30} />
        </React.Fragment>
      );
    else return <React.Fragment>Sign Up</React.Fragment>;
  };

  return (
    <div>
      <h2>New Users</h2>
      <form onSubmit={handleSubmit}>
        <Row>
          <Col xl={6} md={12}>
            <div className="form-group">
              <input
                type="text"
                maxLength={30}
                name="fname"
                id="fname"
                value={firstName}
                className="form-control"
                onChange={e => setFirstName(e.target.value)}
                required
              />
              <label
                className="form-control-placeholder floatingLbl"
                id="fnameLBL"
                htmlFor="fname"
              >
                First Name
              </label>
            </div>
          </Col>
          <Col xl={6} md={12}>
            <div className="form-group">
              <input
                type="text"
                maxLength={30}
                name="lname"
                id="lname"
                value={lastName}
                onChange={e => setLastName(e.target.value)}
                className="form-control"
                required
              />
              <label
                className="form-control-placeholder floatingLbl"
                id="lnameLBL"
                htmlFor="lname"
              >
                Last Name
              </label>
            </div>
          </Col>
          <Col md={12}>
            <div className="form-group">
              <input
                type="text"
                name="email_new"
                id="email_new"
                value={emailAddress}
                pattern="^$|[a-z0-9._%+-]+@[a-z0-9.-]+\.[a-z]{2,}$"
                onChange={e => setEmailAddress(e.target.value)}
                className="form-control"
                required
              />
              <label
                className="form-control-placeholder floatingLbl"
                id="email_newLBL"
                htmlFor="email_new"
              >
                Email
              </label>
            </div>
          </Col>
          <Col md={12}>
            <div className="form-group">
              <input
                type="password"
                maxLength={12}
                name="password_new"
                id="password_new"
                value={password}
                onChange={e => setPassword(e.target.value)}
                className="form-control"
                required
              />
              <label
                className="form-control-placeholder floatingLbl"
                id="password_newLBL"
                htmlFor="password_new"
              >
                Password
              </label>
            </div>
          </Col>
          <Col lg={4}>
            <Button type="submit" disabled={submitting} className="btn-block">
              {getButtonText()}
            </Button>
          </Col>
        </Row>
      </form>
    </div>
  );
};
