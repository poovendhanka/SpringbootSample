# Architecture

## Overview

SpringbootSample is a small Spring Boot 3.5.5 web application running on Java 17. The application exposes an in-memory order-management API. `Application` bootstraps Spring Boot; `OrderController` maps HTTP requests; and `OrderService` validates requests, applies pricing and order-state rules, and stores orders.

## Components

```text
HTTP client
    |
    v
OrderController (/orders)
    |
    v
OrderService
    |
    v
In-memory Map<Integer, Map<String, Object>>
```

- **Application** (`com.example.demo.Application`) starts the Spring application.
- **OrderController** (`com.example.demo.controller.OrderController`) is a REST controller rooted at `/orders`. It delegates all order operations to `OrderService`.
- **OrderService** (`com.example.demo.service.OrderService`) is a Spring `@Service`. It owns the order store, assigns sequential integer IDs starting at 1, validates creation input, calculates amounts, and manages payment and order statuses.
- **Persistence** is process-local only. Orders are held in a `HashMap` and are lost when the application stops. There is no database, repository layer, authentication, or external payment integration.

## Order lifecycle

New orders start with `paymentStatus=PENDING` and `orderStatus=CREATED`. A successful payment changes them to `PAID` and `CONFIRMED`. A payment over 10,000 changes them to `REVIEW_REQUIRED` and `PAYMENT_REVIEW` instead. Cancellation changes an unpaid order directly to `CANCELLED`; a paid order is refunded immediately unless its amount exceeds 5,000, in which case it moves to `REFUND_PENDING`.

Product prices are defined in `OrderService`: laptop 50,000, phone 20,000, keyboard 2,000, and mouse 1,000. Quantities of five or more receive a 10% discount.

## Automation

The `update-documentation.yml` workflow runs manually or on pushes to `Stage` that do not change `docs/**`. It checks out `Stage`, invokes the GitHub Copilot CLI with the code-documentation prompt, and commits generated documentation changes back to the branch.
