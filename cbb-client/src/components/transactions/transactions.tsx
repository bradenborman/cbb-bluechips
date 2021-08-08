import React, { useState, useEffect } from "react";
import { Page } from "../general/page";
import { Row, Col, Table, Card } from "react-bootstrap";
import axios from "axios";
import Loader from "react-loader-spinner";
import { ITransaction } from "../../models/transactionRecord";
import classNames from "classnames";

export interface ITransactionsProps {}
export const Transactions: React.FC<ITransactionsProps> = (
  props: ITransactionsProps
) => {
  //Each time btn is clicked, this amount shows more
  let SCROLL_AMOUNT_INCREASE = 20;

  //Set on UI
  const [activeFilter, setActiveFilter] = useState<string>();

  //Rest call
  const [transactions, setTransactions] = useState<ITransaction[]>();

  const [showTransactionsCount, setShowTransactionsCount] = useState<number>(
    20
  );

  useEffect(() => {
    axios
      .get("/api/transactions")
      .then(response => {
        setTransactions(response.data);
      })
      .catch(error => {
        console.log(error);
      });
  }, []);

  useEffect(() => {
    const scrollOptions: ScrollToOptions = {
      behavior: "smooth",
      top: document.body.scrollHeight
    };

    if (showTransactionsCount > 10) window.scrollTo(scrollOptions);
  }, [showTransactionsCount]);

  const handleFilterChange = (filter: string): void => {
    setActiveFilter(filter);
  };

  const getActiveFilterText = (): JSX.Element => {
    if (activeFilter == undefined)
      return <span>Selete a name or team to filter.</span>;
    if (activeFilter == "") return <span>Active Filter: N/A</span>;

    return (
      <span onClick={e => setActiveFilter("")}>
        Active Filter: <i className="fa fa-minus-circle" /> {activeFilter}
      </span>
    );
  };

  const transactionsTable = (): JSX.Element => {
    if (transactions == undefined)
      return (
        <div className="loading-wrapper">
          <Loader type="TailSpin" color="#00BFFF" height={100} width={100} />
        </div>
      );

    const transactionsRows: JSX.Element[] = transactions
      .filter((row: ITransaction) => {
        if (
          activeFilter != null &&
          activeFilter != undefined &&
          activeFilter != ""
        )
          return row.fullName == activeFilter || row.teamName == activeFilter;
        return true;
      })
      .slice(0, showTransactionsCount)
      .map((record, index) => {
        return (
          <tr key={index}>
            <td
              onClick={e => handleFilterChange(record.fullName)}
              className="name"
            >
              {record.fullName}
            </td>
            <td>
              <span
                className={classNames(
                  "badge",
                  record.tradeAction == "BUY"
                    ? "badge-primary"
                    : "badge-success"
                )}
              >
                {record.tradeAction}
              </span>
            </td>
            <td
              onClick={e => handleFilterChange(record.teamName)}
              className="team"
            >
              {record.teamName}
            </td>
            <td>${record.cashTraded.toLocaleString()}</td>
            <td>{record.strTimeofTransaction}</td>
          </tr>
        );
      });

    const showMore = () => {
      if (transactions.length > showTransactionsCount) {
        const colapseBtn =
          showTransactionsCount > 20 ? (
            <button onClick={e => setShowTransactionsCount(20)}>
              Collapse Older
            </button>
          ) : null;
        return (
          <td colSpan={5}>
            <button
              onClick={e =>
                setShowTransactionsCount(
                  showTransactionsCount + SCROLL_AMOUNT_INCREASE
                )
              }
            >
              Show Older Transactions
            </button>
            {colapseBtn}
          </td>
        );
      }
    };

    return (
      <Table responsive>
        <thead>
          <tr className="thead-light">
            <th>
              <i className="fas fa-user"></i> Name
            </th>
            <th>Action</th>
            <th>Team</th>
            <th>
              <i className="fas fa-dollar-sign"></i> Amount
            </th>
            <th>
              <i className="fas fa-clock"></i> Date
            </th>
          </tr>
        </thead>
        <tbody id="myTable">{transactionsRows}</tbody>
        <tfoot>
          <tr>{showMore()}</tr>
        </tfoot>
      </Table>
    );
  };

  return (
    <Page pageId="transactions">
      <Row>
        <Col lg={12}>
          <Card>
            <Card.Text id="filter-text">{getActiveFilterText()}</Card.Text>
            {transactionsTable()}
          </Card>
        </Col>
      </Row>
    </Page>
  );
};
