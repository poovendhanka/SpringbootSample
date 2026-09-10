# API Reference

The API is served by the Spring Boot application under the `/orders` resource path. Responses are JSON objects represented internally as maps.

## Create an order

`POST /orders`

Query parameters:

| Parameter | Type | Required | Description |
|---|---|---:|---|
| `customer` | string | Yes | Customer name; must not be blank |
| `product` | string | Yes | Product name: `laptop`, `phone`, `keyboard`, or `mouse` |
| `quantity` | integer | Yes | Must be greater than zero |

Successful response:

```json
{
  "id": 1,
  "customer": "Avery",
  "product": "mouse",
  "quantity": 2,
  "amount": 2000.0,
  "paymentStatus": "PENDING",
  "orderStatus": "CREATED"
}
```

Invalid input returns a response containing `status=FAILED` and a `message`. Unknown products return `status=FAILED` with `message=Unknown product`.

## Get an order

`GET /orders/{id}`

Returns the complete order object. If the ID does not exist, the response contains `status=NOT_FOUND` and `message=Order does not exist`.

## Get an order summary

`GET /orders/{id}/summary`

Returns the same order object as `GET /orders/{id}`. Despite the summary route name, no reduced response shape is currently implemented.

## Pay for an order

`POST /orders/{id}/pay`

An eligible order is updated to `paymentStatus=PAID` and `orderStatus=CONFIRMED`. A cancelled order, an already-paid order, or a missing order returns a `FAILED` or `NOT_FOUND` response. Orders over 10,000 return `status=REVIEW_REQUIRED` and are updated to `paymentStatus=REVIEW_REQUIRED` and `orderStatus=PAYMENT_REVIEW`.

## Cancel an order

`POST /orders/{id}/cancel`

Cancels an order and returns the updated order. A paid order is marked `paymentStatus=REFUNDED` unless its amount exceeds 5,000; high-value paid orders instead receive `status=REFUND_PENDING` and remain in `orderStatus=REFUND_PENDING` pending refund approval. Missing or already-cancelled orders return an error response.

## Check cancelled state

`POST /orders/{id}/canceled`

Returns the complete order object for the requested ID. This compatibility endpoint is named as a cancellation check but does not change state or return a separate boolean.

## Get status

`GET /orders/{id}/status`

Returns only the current status fields:

```json
{
  "orderId": 1,
  "orderStatus": "CONFIRMED",
  "paymentStatus": "PAID"
}
```

