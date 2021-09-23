export interface ITeamExchangeDetailsResponse {
  userId: string;
  purchasingPower: number;
  teamId: string;
  teamName: string;
  seed: number;
  currentMarketPrice: number;
  sharesOutstanding: string;
  teamPlayingNext: string;
  startTime: string;
  locked: boolean;
  pointSpread: number;
  topHolders: string[];
  maximumCanPurchase: number;
  amountSharesOwned: number;
}
