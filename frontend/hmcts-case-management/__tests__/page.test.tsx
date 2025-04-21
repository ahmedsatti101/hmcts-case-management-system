import "@testing-library/jest-dom";
import { render, screen } from "@testing-library/react";
import Page from "../src/app/page";

describe("Page", () => {
  beforeEach(() => {
    render(<Page />);
  });

  it("render 'Tasks' heading", () => {
    const heading = screen.getByRole("heading", { level: 1 });

    expect(heading).toBeInTheDocument();
  });

  it("render button to add new task", () => {
    const button = screen.getByRole("button", { name: "Add new task" });

    expect(button).toBeInTheDocument();
  });
});
