# Charity Donation Platform

## Overview
This is a modular, menu-driven Java application for simulating a charity donation platform. It supports donor and charity flows, analytics, and is designed with clean separation between models, DAOs, services, and utilities.

## Features
- Donor registration/login and donation flows
- Charity registration/login and campaign/fundraiser management (planned)
- View and filter charities, campaigns, fundraisers
- View donation history
- Run analytics/test queries
- Local and hosted database support

## Getting Started

### Prerequisites
- Java 17+
- Gradle
- MySQL (or compatible RDBMS)

### Local Database Setup
1. **Run the application:**
   - On first run, choose `1. Use local Database` in the menu.
   - Enter your DB URL (e.g. `jdbc:mysql://localhost:3306/`), username, and password.
   - The app will automatically:
     - Create Database
     - Create schema (tables, relationships)
     - Insert dummy data for testing

### Running the Application
1. Build with Gradle:
   ```sh
   ./gradlew build
   ```
2. Run:
   ```sh
   ./gradlew run
   ```
3. Follow the CLI prompts:
   - Choose database setup
   - Use the main menu to access Test Queries, Donor Flow, or Charity Flow

## Donor Flow
- Register or login as a donor
- Make direct, campaign, or fundraiser donations
- View all charities, campaigns, fundraisers
- Filter campaigns/fundraisers by category
- View your donation history

## Test Queries
- Run analytics on donations, donor segments, campaign/fundraiser progress, etc.

## Schema Explanation
- **Donor:** Stores donor info (id, name, email, password, city, state, country, age, gender, income range, referrer source)
- **Charity:** Stores charity info (charity_id, name, email, password, description, website_url, ack_url, receipt_url, category_id, is_active, created_at)
- **Campaign:** Fundraising campaigns (campaign_id, charity_id, category_id, title, description, rec_url, ack_url, is_active, start_date, end_date)
- **Fundraiser:** Individual fundraisers (fundraiser_id, charity_id, category_id, title, description, goal_amount, current_amount, rec_url, ack_url, is_active, start_date, end_date, created_at)
- **Category:** Categories for charities, campaigns, fundraisers (id, name, description)
- **DirectDonation:** Direct donations (transaction_id, donor_id, charity_id, amount, receipt_status, receipt_url, ack_url, timestamp)
- **CampaignTransaction:** Donations to campaigns (transaction_id, donor_id, campaign_id, amount, receipt_status, receipt_url, ack_url, timestamp)
- **FundraiserTransaction:** Donations to fundraisers (transaction_id, donor_id, fundraiser_id, amount, receipt_status, receipt_url, ack_url, timestamp)
- **Other tables:** For preferences, summaries, caches, etc. (see `src/main/resources/sql/core/` for full DDL)

## Folder Structure
- `src/main/java/org/example/model/` - Data models
- `src/main/java/org/example/dao/` - Data access objects
- `src/main/java/org/example/service/` - Business logic and flows
- `src/main/java/org/example/utils/` - Utilities (DB connection, schema, dummy data)
- `src/main/resources/sql/core/` - SQL schema scripts
- `src/main/resources/sql/requirements/` - Contains queries as per requirement doc.

## Extending/Customizing
- To add new flows (e.g., Charity Flow), implement in the service layer and update the simulator menu.
- To change schema, update SQL files and corresponding models/DAOs.

## Support
For issues or feature requests, please open an issue in this repository.

---

**Enjoy simulating and testing your charity donation platform!**
