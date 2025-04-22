import "@testing-library/jest-dom";
import { render, screen } from "@testing-library/react";
import Page from "../src/app/page";
import axios from "axios";

jest.mock("axios");

describe("Page", () => {
  const mockData = [
    {
      id: 1,
      title: "Test task",
      description: "Test task description",
      status: "Done",
    },
    {
      id: 2,
      title: "Test task 2",
      description: null,
      status: "Pending...",
    }
  ];

  it("render 'Tasks' heading", () => {
    render(<Page />);
    const heading = screen.getByRole("heading", { level: 1 });

    expect(heading).toBeInTheDocument();
  });

  it("render button to add new task", () => {
    render(<Page />);
    const button = screen.getByRole("button", { name: "Add new task" });

    expect(button).toBeInTheDocument();
  });

  it("render task title", async () => {
    (axios.get as jest.Mock).mockResolvedValue({
      data: mockData,
    });

    render(<Page />);
    const title = await screen.findAllByTestId("task-title");

    expect(title.length).toEqual(2);
  });
});
