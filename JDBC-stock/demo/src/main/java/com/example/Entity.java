package com.example;

public sealed interface Entity permits  Stock, Product, RestockRequest { }
