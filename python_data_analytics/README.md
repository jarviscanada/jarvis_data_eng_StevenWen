# Introduction
- Describe the business context of this project (please do not copy-paste text from the project kick-off ticket)
London Gift Shop (LGS) is a UK-based online retailer specializing in giftware products, with a significant portion of its customers being wholesalers. Although the company has operated its e-commerce platform for over a decade, its revenue growth has recently plateaued. To address this challenge, the LGS marketing team aims to better understand customer purchasing behavior and leverage data-driven insights to improve sales and marketing effectiveness.

Due to limited internal IT capacity, LGS engaged Jarvis consulting services to deliver a proof-of-concept (PoC) analytics project. The goal of this project is to analyze historical transaction data and extract actionable insights that can support targeted marketing initiatives. In this project, customer shopping behavior is analyzed through sales trends, order activity, cancellations, customer lifecycle metrics, and RFM (Recency, Frequency, Monetary) segmentation. The results of this analysis enable the LGS marketing team to design targeted campaigns such as personalized emails, promotional events, and customer-specific offers. By identifying high-value customers, new customers, and customers at risk of churn, LGS can more effectively allocate marketing resources and improve customer retention, ultimately driving revenue growth.

This Python-based data analytics can be used to transform raw transactional data into meaningful business insights. The analysis is implemented using Jupyter Notebook with Pandas and NumPy, while PostgreSQL is used as the underlying data store, in a data warehouse environment. The final deliverable provides LGS with a clear analytical foundation that can be expanded into a full production-grade analytics solution in the future.

# Implementaion
## Project Architecture
This project follows a layered architecture that separates the LGS web application, backend services, and data analytics components. Customers interact with the London Gift Shop (LGS) web application through a browser-based frontend. Static web assets such as HTML, CSS, JavaScript are delivered via a content delivery network (CDN) to ensure fast and reliable access. By backend APIs it stores order placement and product to a Azure relational database. The backend services communicate with the relational database that stores operational data such as invoices, customers, products, and order details. Through an ETL process all the live data will be loaded into a separate analytics database.

In this project’s proof-of-concept implementation, the analytics layer is simulated using a PostgreSQL database running in Docker as a data warehouse. A Jupyter Notebook environment is used for data wrangling, analysis, and visualization using Python-based analytics tools. This setup allows historical sales and customer data from the LGS web application to be transformed into actionable insights while remaining decoupled from production systems.

![Architecture](assets/architecture.png)

## Data Analytics and Wrangling
The data analytics and wrangling process for this project is implemented in a Jupyter Notebook, which can be found here:
./python_data_wrangling/retail_data_analytics_wrangling.ipynb

Using Python-based data analytics libraries such as Pandas, NumPy, and Matplotlib, raw transactional data from the LGS retail database is cleaned, transformed, and analyzed to uncover customer and sales patterns. The analysis includes data preprocessing, aggregation, statistical analysis, and visualization to support business decision-making.

### Analytics 
Sales Analysis
Monthly sales amounts and growth rates were calculated to identify revenue trends and seasonality. This helps LGS understand peak sales periods and revenue fluctuations over time.

Order and Cancellation Analysis
Placed and canceled orders were analyzed at a monthly level to measure order stability and cancellation behavior. This insight can be used to evaluate customer satisfaction and operational issues.

Customer Activity Analysis
Monthly active users were identified based on unique customer purchases, along with new and returning customer trends. This provides visibility into customer acquisition and retention performance.

Customer Segmentation (RFM Analysis)
Customers were segmented using Recency, Frequency, and Monetary (RFM) metrics to classify customers into meaningful groups such as Champions, Loyal Customers, At Risk, and Hibernating customers. This segmentation highlights differences in customer value and engagement levels.

Based on the analytics results, LGS revenue is highly seasonal with significant sales spikes during the holiday period, driven primarily by existing customers. Customer acquisition remains relatively low, while retention and repeat purchases contribute the majority of sales. RFM segmentation reveals strong high-value customer groups alongside a large inactive customer base, indicating opportunities for both loyalty-based growth and re-engagement. By focusing on targeted holiday campaigns, strengthening customer retention strategies, and reactivating at-risk customers using RFM insights, LGS can increase revenue stability and maximize customer lifetime value.

# Improvements
- Parameterize the notebook to support different date ranges and markets.
- Add automated data quality checks (missing values, negative quantities, invalid dates).
- Persist derived tables (monthly metrics, RFM scores) back into PostgreSQL for reuse.