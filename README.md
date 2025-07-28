# Charity Donation Platform Simulation

A comprehensive Java-based simulation of a charity donation platform that demonstrates database design, user management, donation processing, and analytics. This project serves as a practical implementation for learning SQL, Java, and database-driven application development.

## 🚀 Features

### User Management
- **Donor Registration & Login**: Complete user registration with profile information
- **Contact Preferences**: Multiple contact methods (Email, SMS, Phone) with priority ordering
- **Payment Preferences**: Multiple payment methods with primary selection
- **Profile Management**: View and manage donor profiles

### Charity Management
- **Charity Registration & Login**: Charity owner authentication
- **Campaign Management**: Create, edit, activate/deactivate campaigns
- **Fundraiser Management**: Create and manage fundraising campaigns
- **Dashboard Analytics**: Comprehensive reporting and insights

### Donation System
- **Direct Donations**: Donate directly to charities
- **Campaign Donations**: Support specific campaigns
- **Fundraiser Donations**: Contribute to fundraising goals
- **Receipt Management**: Automated receipt generation and tracking

### Analytics & Reporting
- **Donation Analytics**: Track donations by type, time period, and demographics
- **Financial Reports**: Revenue summaries, trends, and performance metrics
- **Campaign Performance**: Individual campaign analytics and insights
- **Donor Insights**: Donor behavior and contribution patterns

## 🛠️ Technology Stack

- **Java 17+**: Core application language
- **Gradle**: Build automation and dependency management
- **MySQL**: Database system (supports both local and AWS RDS)
- **JDBC**: Database connectivity and operations

## 📋 Prerequisites

- Java 17 or higher
- MySQL 8.0 or higher
- Gradle (or use the included Gradle wrapper)

## 🗄️ Database Setup

### Option 1: Local MySQL Setup
1. Install MySQL on your system
2. Setup the MySQL Server, get URL (for eg : localhost:3306), get root username and password as well.
3. Choose local database option during program runtime. Put your URL like 
```
"jdbc:mysql://localhost:3306/"
```
4. Enter username and password, press Enter.
5. Schema and dummy data will be automatically generated.


### Option 2: Choose Hosted Database
1. Select hosted database option during runtime and you are good to go.

### Schema Creation
The application automatically creates all necessary tables and relationships:
- Core entities: donors, charities, campaigns, fundraisers
- Transaction tables: direct_donations, campaign_transactions, fundraiser_transactions
- Preference tables: user_contact_preference, user_payment_preference
- Cache tables: campaign_cache, fundraiser_cache, donor_donation_summary
- Type tables: contact_method_type, payment_method_type, category, charity_category

## 🚀 Running the Application

### Build the Project
```bash
./gradlew build
```

### Run the Application
```bash
./gradlew run
```

## 🎯 User Flows

### Donor Flow
1. **Registration**
   - Enter personal information (name, email, location, demographics)
   - Set up contact preferences (primary + optional secondary methods)
   - Configure payment methods (primary + optional secondary methods)

2. **Main Menu Options**
   - View profile information
   - Browse charities, campaigns, and fundraisers
   - Filter by categories
   - View donation history
   - Manage contact and payment preferences

3. **Donation Process**
   - Select donation target (charity/campaign/fundraiser)
   - Enter donation amount
   - Automatic processing using primary payment method
   - Receipt generation via preferred contact method

### Charity Owner Flow
1. **Registration & Login**
   - Register charity with basic information
   - Login to access dashboard

2. **Dashboard Features**
   - Overview of total donations and recent activity
   - Campaign management (create, edit, activate/deactivate)
   - Fundraiser management with progress tracking
   - Comprehensive analytics and reporting

3. **Campaign Management**
   - Create campaigns with flexible end dates (auto-set to 100 years if not specified)
   - Edit campaign details
   - Toggle campaign status
   - View campaign performance metrics

4. **Analytics & Reports**
   - Donation analytics by type and time period
   - Financial reports and revenue summaries
   - Campaign and fundraiser performance tracking
   - Receipt management and statistics

## 📊 Database Schema Overview

### Core Tables
- **donors**: User profiles with demographic information
- **charities**: Charity organizations and their details
- **campaigns**: Time-bound fundraising campaigns
- **fundraisers**: Goal-based fundraising initiatives

### Transaction Tables
- **direct_donations**: Direct charity donations
- **campaign_transactions**: Campaign-specific donations
- **fundraiser_transactions**: Fundraiser-specific donations

### Preference Tables
- **user_contact_preference**: Donor contact method preferences
- **user_payment_preference**: Donor payment method preferences

### Type Tables
- **contact_method_type**: Available contact methods (Email, SMS, Phone)
- **payment_method_type**: Available payment methods (Credit Card, Bank Transfer, etc.)
- **category**: Campaign/fundraiser categories
- **charity_category**: Charity type categories

### Cache Tables
- **campaign_cache**: Performance-optimized campaign data
- **fundraiser_cache**: Performance-optimized fundraiser data
- **donor_donation_summary**: Aggregated donor contribution data

## 🔧 Configuration

### Database Connection
Update `src/main/java/org/example/utils/ConnectionUtil.java`:

```java
// For local MySQL
private static final String URL = "jdbc:mysql://localhost:3306/charity_donation_platform";
private static final String USERNAME = "your_username";
private static final String PASSWORD = "your_password";

// For AWS RDS
private static final String URL = "jdbc:mysql://your-rds-endpoint:3306/charity_donation_platform";
```

### Application Settings
- **Cache Strategy**: Write-back cache implementation for performance
- **Receipt Generation**: Automatic receipt status tracking
- **Input Validation**: Comprehensive validation for all user inputs
- **Error Handling**: Graceful error handling with user-friendly messages

## 📈 Analytics Queries

The application includes pre-built SQL queries for various analytics:

### Donor Analytics
- Average donation by age group
- Average donation by gender
- Average donation by income range
- Donor acquisition trends

### Campaign Analytics
- Total donations per campaign
- Campaign receipt issuance status
- Fundraisers nearing fundraising goals

### Financial Analytics
- Revenue summaries and trends
- Campaign and fundraiser performance
- Donor insights and demographics


## 📁 Project Structure

```
charity-donation-platform-simulation/
├── build.gradle.kts                 # Gradle build configuration
├── src/
│   ├── main/
│   │   ├── java/org/example/
│   │   │   ├── dao/                 # Data Access Objects
│   │   │   ├── model/               # Entity models
│   │   │   ├── service/             # Business logic services
│   │   │   ├── utils/               # Utility classes
│   │   │   ├── Main.java            # Application entry point
│   │   │   └── Simulator.java       # Main application controller
│   │   └── resources/
│   │       └── sql/                 # SQL schema and queries
│   │           ├── core/            # Core table definitions
│   │           └── requirements/    # Analytics queries
│   └── test/                        # Test files
└── README.md                        # This file
```

## 🔒 Security Features

- **Input Validation**: Comprehensive validation for all user inputs
- **SQL Injection Prevention**: Prepared statements throughout
- **Error Handling**: Secure error messages without exposing system details
- **Data Integrity**: Foreign key constraints and referential integrity

## 🚀 Performance Optimizations

- **Cache Tables**: Write-back cache strategy for analytics queries
- **Indexed Queries**: Optimized database queries with proper indexing
- **Connection Pooling**: Efficient database connection management
- **Batch Operations**: Optimized bulk data operations

## 📝 Usage Examples

### Creating a Campaign
```
1. Login as charity owner
2. Navigate to Campaign Management
3. Select "Create New Campaign"
4. Enter campaign details
5. Set end date (or press Enter for 100-year duration)
6. Campaign is created and ready for donations
```

### Making a Donation
```
1. Login as donor
2. Browse charities/campaigns/fundraisers
3. Select donation target
4. Enter donation amount
5. System processes payment via primary method
6. Receipt sent via preferred contact method
```

### Viewing Analytics
```
1. Login as charity owner
2. Access Dashboard or Analytics menu
3. Select desired report type
4. View comprehensive performance metrics
5. Export or analyze data as needed
```

## 🤝 Contributing

1. Fork the repository
2. Create a feature branch
3. Make your changes
4. Add tests for new functionality
5. Submit a pull request

## 📄 License

This project is created for educational purposes and SQL course assignments.

## 🆘 Support

For issues or questions:
1. Check the error logs in the console output
2. Verify database connection settings
3. Ensure all prerequisites are met
4. Review the database schema for any missing tables
5. Create issues on repository or contact at jenishtogadiya549@gmail.com
6. For more contact information visit : https://jenish.site/contact

## 🔄 Future Enhancements

- Web-based user interface
- Real-time notifications
- Advanced payment processing
- Mobile application
- API endpoints for third-party integrations
- Advanced analytics and machine learning insights
- Multi-language support
- Enhanced security features
- Redis implementation for actual caching

---

**Note**: This is a simulation system designed for educational purposes. In a production environment, additional security measures, encryption, and compliance features would be implemented.
