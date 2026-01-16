-- Show table schema 
\d+ retail;

-- Show first 10 rows
SELECT * FROM retail limit 10;

-- Check # of records
SELECT COUNT(*) AS total_records
FROM retail;

-- number of clients (e.g. unique client ID)
SELECT COUNT(DISTINCT customer_id) AS count
FROM public.retail
WHERE customer_id IS NOT NULL;

-- Q3: number of clients (unique client ID)
SELECT COUNT(DISTINCT customer_id) AS count
FROM public.retail
WHERE customer_id IS NOT NULL;

-- Q4: invoice date range (min/max dates)
SELECT MIN(invoice_date) AS min,
       MAX(invoice_date) AS max
FROM public.retail;

-- Q5: number of SKU (unique stock code)
SELECT COUNT(DISTINCT stock_code) AS count
FROM public.retail
WHERE stock_code IS NOT NULL;

-- Q6: average invoice amount excluding negative invoices
SELECT AVG(invoice_amount) AS avg
FROM (
  SELECT invoice_no,
         SUM(unit_price * quantity) AS invoice_amount
  FROM public.retail
  GROUP BY invoice_no
  HAVING SUM(unit_price * quantity) > 0
) t;

-- Q7: total revenue (sum of unit_price * quantity)
SELECT SUM(unit_price * quantity) AS sum
FROM public.retail;

-- Q8: total revenue by YYYYMM
SELECT TO_CHAR(invoice_date, 'YYYYMM') AS yyyymm,
       SUM(unit_price * quantity)      AS sum
FROM public.retail
GROUP BY 1
ORDER BY 1;

