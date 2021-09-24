import { TransactionType } from "../components/trade/components/transactionSlider";

export interface TransactionAction {
  currentTransactionCost: string;
  lastTransactionType: TransactionType | null;
}
