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
    },
  ];

  it("render 'Tasks' heading", async () => {
    (axios.get as jest.Mock).mockResolvedValue({ data: [] });

    render(<Page />);
    const heading = await screen.findByRole("heading", { level: 1 });

    expect(heading).toBeInTheDocument();
  });

  it("render button to add new task", async () => {
    (axios.get as jest.Mock).mockResolvedValue({ data: [] });

    render(<Page />);
    const button = await screen.findByRole("button", { name: "Add new task" });

    expect(button).toBeInTheDocument();
  });

  it("render task title", async () => {
    (axios.get as jest.Mock).mockResolvedValue({
      data: mockData,
    });

    render(<Page />);
    const titles = await screen.findAllByTestId("task-title");

    expect(titles.length).toEqual(2);
  });

  it("render task description", async () => {
    (axios.get as jest.Mock).mockResolvedValue({
      data: mockData,
    });

    render(<Page />);
    const descriptions = await screen.findAllByTestId("task-description");

    expect(descriptions.length).toEqual(2);
  });

  it("render task status", async () => {
    (axios.get as jest.Mock).mockResolvedValue({
      data: mockData,
    });

    render(<Page />);
    const statuses = await screen.findAllByTestId("task-status");

    expect(statuses.length).toEqual(2);
  });

  it("render loading text while data is being fetched", () => {
    render(<Page />);
    const text = screen.getByText("Loading...");

    expect(text).toBeInTheDocument();
  });
});
