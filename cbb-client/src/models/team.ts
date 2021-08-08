export interface ITeam {
  teamId: number;
  teamName: string;
  eliminated?: boolean;
  seed: number;
  marketPrice: number;
  sharesOutstanding: number;
  imgSrcName: string;
  currentMarketPrice?: number;
  priceHistory?: string[];
  priceHistoryString?: string; //Used
  locked: boolean;
  pointSpread: number;
  nextGameTime?: string;
  doesUserOwn?: boolean;
}
