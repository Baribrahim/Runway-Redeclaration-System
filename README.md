# Runway Redeclaration Tool

## Overview

The **Runway Redeclaration Tool** is a powerful application designed to assist airport authorities in recalculating runway parameters when an obstacle is present. This tool provides visualizations and calculations to help quickly determine if limited operations can continue on the runway under reduced distances for take-offs and landings. While it does not replace the official process, it serves as a valuable decision-making aid.

## Key Features

- **Runway Distance Recalculation**: Automatically recalculates Take-off Run Available (TORA), Take-off Distance Available (TODA), Accelerate-Stop Distance Available (ASDA), and Landing Distance Available (LDA) when there is an obstacle on the runway.
- **Obstacle Visualisation**: Provides 2D top-down and side-on visualizations to help understand how the obstacle affects runway operations.
- **Import/Export Functionality**: Supports XML import/export of airport and obstacle data.
- **User Roles and Multi-user Collaboration**: Role-based access (Admin, Editor, Viewer) to allow multiple users to collaborate on recalculations and visualizations.
- **Reporting**: Export recalculated runway parameters and visualizations as PDF reports for further analysis.
- **Real-time Notifications**: Displays system notifications for actions like obstacle addition and parameter changes.

## Technologies Used

- **Java**: Core language for application logic and functionality.
- **XML**: Used for data import/export (obstacle, runway details).
- **JavaFX**: For the GUI, enabling 2D visualizations of runways and obstacles.
- **JUnit**: For unit testing and ensuring software robustness.

## How It Works

1. **Runway Data Input**: The user logs in and inputs standard runway parameters and details of the obstacle (height, distance from threshold, etc.).
2. **Automatic Calculations**: Based on the provided inputs, the tool recalculates the TORA, TODA, ASDA, and LDA values, considering necessary safety margins (e.g., RESA, Blast Protection).
3. **Visualisations**: The tool offers both top-down and side-on views of the runway, showing how the obstacle affects the available runway distances.
4. **Comparison**: Users can compare the original and recalculated runway parameters to decide if continuing operations is feasible.
5. **Report Generation**: After finalising the recalculations, users can generate and export a detailed report (including visualizations) in PDF format for decision-makers.
6. **Multi-user Collaboration**: Team members can collaborate on the same data in real time, with role-based access for better control over modifications.

## Demo

To get a better understanding of how the application works, please watch the demo video: [Demo.mp4](Demo.mp4).

