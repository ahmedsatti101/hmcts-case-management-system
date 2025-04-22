import axios from "axios";

export const getAllTasks = async () => {
  try {
    const response = await axios
      .get("http://localhost:8080/api/task");
    return response.data;
  } catch (err) {
    return err;
  }
}
