import React, { useState } from "react";
import { Row, Col, Button } from "react-bootstrap";
import { useHistory } from "react-router";

import axios, { AxiosRequestConfig } from "axios";
import classNames from "classnames";
import Loader from "react-loader-spinner";

export interface ILoginProps {
  emailToAttemptLogin: string | null;
  setIsLoggedIn: (x: boolean) => void;
}

export const Login: React.FC<ILoginProps> = (props: ILoginProps) => {
  const history: any = useHistory();

  const [email, setEmail] = useState<string>(props?.emailToAttemptLogin);
  const [password, setPassword] = useState<string>();

  const [loginError, setLoginError] = useState<boolean>(false);

  const [resettingEmail, setResettingEmail] = useState<boolean>(false);
  const [resettingEmailMessage, setResettingEmailMessage] = useState<string>();

  const handleLogin = (e: any) => {
    e.preventDefault();

    const config: AxiosRequestConfig = {
      headers: { "Content-Type": "application/x-www-form-urlencoded" }
    };

    axios
      .post("/user/login?email=" + email + "&password=" + password, {}, config)
      .then(response => {
        props.setIsLoggedIn(true);
        history.replace("/portfolio");
      })
      .catch(error => {
        console.log("ERROR LOGGING IN");
        setPassword(""); //clear out on failure
        setLoginError(true);
      });
  };

  const handleForgotPassword = (e: any): void => {
    let emailToSend = email;

    if (emailToSend == null || !emailToSend.includes("@"))
      emailToSend = prompt(
        "Please enter email to recover password",
        emailToSend
      );

    setResettingEmail(true);
    axios
      .post(`/api/recover-email?emailToRecover=${emailToSend}`)
      .then(response => {
        setResettingEmail(false);
        setResettingEmailMessage(response.data);
      })
      .catch(error => {
        console.log("ERROR Resetting email");
      });
  };

  const recoverPasswordBtn = (): JSX.Element => {
    if (resettingEmailMessage != undefined)
      return <div>{resettingEmailMessage}</div>;

    if (resettingEmail)
      return (
        <Button onClick={handleForgotPassword} variant="link">
          <Loader type="ThreeDots" color="#00BFFF" height={20} width={30} />
        </Button>
      );
    return (
      <Button onClick={handleForgotPassword} variant="link">
        Recover Password?
      </Button>
    );
  };

  return (
    <div>
      <h2>Returning Users</h2>
      <form onSubmit={handleLogin}>
        <Row>
          <Col md={12}>
            <div className="form-group">
              <input
                type="text"
                name="email"
                id="email"
                className={classNames("form-control", { error: loginError })}
                value={email}
                onChange={e => setEmail(e.target.value)}
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
                name="password"
                id="password"
                value={password}
                className={classNames("form-control", { error: loginError })}
                onChange={e => setPassword(e.target.value)}
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
          <Col xl={12}>
            <div className="recover-password text-center">
              {recoverPasswordBtn()}
            </div>
          </Col>
          <Col lg={4}>
            <Button type="submit" className="btn-block submit">
              Submit
            </Button>
          </Col>
        </Row>
      </form>
    </div>
  );
};
