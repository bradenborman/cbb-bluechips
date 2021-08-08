import { ITeam } from "../models/team";
import { IMatchup } from "../models/matchup";

export const team1: ITeam = {
  teamId: 1,
  teamName: "Duke",
  seed: 45,
  sharesOutstanding: 36,
  imgSrcName: "Duke.png",
  marketPrice: 5000,
  isLocked: false,
  pointSpread: 7.5,
  priceHistory: ["64:5000", "32:4500", "16:6400"],
  teamplayingNextName: "Kentucky"
};

export const team2: ITeam = {
  teamId: 2,
  teamName: "Kentucky",
  seed: 6,
  sharesOutstanding: 84,
  imgSrcName: "Kentucky.png",
  marketPrice: 5000,
  isLocked: false,
  pointSpread: -7.5,
  priceHistory: ["64:5000", "32:6500"],
  teamplayingNextName: "Duke"
};

export const matchup1: IMatchup = {
  team1,
  team2,
  startTime: "1:45 PM"
};

export const matchup2: IMatchup = {
  team1: {
    teamId: 3,
    teamName: "Alabama",
    seed: 1,
    sharesOutstanding: 64,
    imgSrcName: "Alabama.png",
    marketPrice: 5000,
    isLocked: false,
    pointSpread: 5,
    priceHistory: ["64:5000"]
  },
  team2: {
    teamId: 4,
    teamName: "Utah",
    seed: 10,
    sharesOutstanding: 79,
    imgSrcName: "Utah.png",
    marketPrice: 5000,
    isLocked: false,
    pointSpread: -5,
    priceHistory: ["64:5000"]
  },
  startTime: "3:30 PM"
};

export const matchup3: IMatchup = {
  team1: {
    teamId: 3,
    teamName: "Tennessee",
    seed: 1,
    sharesOutstanding: 64,
    imgSrcName: "Tennessee.png",
    marketPrice: 5000,
    isLocked: false,
    pointSpread: 5,
    priceHistory: ["64:5000"]
  },
  team2: {
    teamId: 4,
    teamName: "Virginia",
    seed: 10,
    sharesOutstanding: 79,
    imgSrcName: "Virginia.png",
    marketPrice: 5000,
    isLocked: false,
    pointSpread: -5,
    priceHistory: ["64:5000"]
  },
  startTime: "7:30 PM"
};

export const matchup4: IMatchup = {
  team1: {
    teamId: 3,
    teamName: "Michigan",
    seed: 1,
    sharesOutstanding: 64,
    imgSrcName: "Michigan.png",
    marketPrice: 5000,
    isLocked: false,
    pointSpread: 5,
    priceHistory: ["64:5000"]
  },
  team2: {
    teamId: 4,
    teamName: "Dayton",
    seed: 10,
    sharesOutstanding: 79,
    imgSrcName: "Dayton.png",
    marketPrice: 5000,
    isLocked: false,
    pointSpread: -5,
    priceHistory: ["64:5000"]
  },
  startTime: "9:45 PM"
};
