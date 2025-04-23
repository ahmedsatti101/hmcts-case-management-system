# HMCTS Case Management System

This is the frontend part of the case management system for HMCTS written with `Next.js` and `TypeScript`.

## Running locally
Once you have cloned this repository on your machine, naviagte to the `frontend/hmcts-case-management` folder and perform the following:

> [!NOTE]
> Make sure the [API](../../backend/case-management/README.md) is up and running so you are able to perform actions like creating and updating tasks as well as viewing them

1. Install dependencies
    ```bash
    npm install
    ```

2. Run the app
    ```bash
    npm run dev
    ```
Open [http://localhost:3000](http://localhost:3000) in your browser to view the application.

3. Run tests
    ```bash
    npm run test
    ```
You can look at the `__tests__` folder in the root of this directory to view the available tests.

All of the frontend code is in the `src/app` folder.
