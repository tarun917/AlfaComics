package com.alfacomics.presentation.ui.screens.payment

import kotlin.random.Random

object PaymentProcessor {
    fun processPayment(method: String, amount: String): Boolean {
        // Simulate payment processing
        // In a real app, integrate with a payment gateway like Razorpay or Paytm here
        // For testing, randomly return success or failure
        return Random.nextBoolean() // 50% chance of success or failure
    }
}