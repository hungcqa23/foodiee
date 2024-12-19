package com.example.foodiee.Navigation

sealed class Routes(val route: String) {
    object HomeScreen : Routes("homeScreen")
    object RoleSelectionScreen : Routes("roleSelectionScreen")
    object SignUpScreen : Routes("signUpScreen")
    object LoginScreen : Routes("loginScreen")

    object DetailedCourseScreen : Routes("detailedCourseScreen")
    object OrderDetailScreen : Routes("orderDetail/{orderId}") {
        fun createRoute(orderId: String) = "orderDetail/$orderId"
    }
    object CartScreen : Routes("cartScreen/{cartId}") {
        fun createRoute(cartId: String) = "cartScreen/$cartId"
    }
    object ProfileScreen : Routes("profileScreen")
    object EditProfileScreen : Routes("editProfileScreen")
    object PersonalInformationScreen : Routes("personalInformationScreen")
    object StatisticScreen: Routes("statisticScreen")
    object EditDishScreen: Routes("editDishScreen")
    object InventoryScreen: Routes("inventoryScreen")
    object OrdersManagementScreen: Routes("ordersManagementScreen")
    object PeopleManagementScreen: Routes("peopleManagementScreen")
    object DishDescriptionScreen: Routes("dishDescriptionScreen/{dishId}")
}