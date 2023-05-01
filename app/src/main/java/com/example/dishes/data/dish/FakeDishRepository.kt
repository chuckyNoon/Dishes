package com.example.dishes.data.dish

import com.example.dishes.data.dish.model.Dish
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import javax.inject.Inject

class FakeDishRepository @Inject constructor() : DishRepository {

    override suspend fun getDishes(): List<Dish> = withContext(Dispatchers.IO) {
        imitateHeaveWork()
        dishes
    }

    override suspend fun deleteDish(id: String) = withContext(Dispatchers.IO) {
        imitateHeaveWork()
    }

    override suspend fun getDishById(id: String) = withContext(Dispatchers.IO) {
        dishes.firstOrNull { it.id == id }
    }

    private suspend fun imitateHeaveWork() = delay(2000)

    private val dishes: List<Dish> = listOf(
        Dish(
            "5ed8da011f071c00465b2026",
            "Бургер \"Америка\"",
            "320 г • Котлета из 100% говядины (прожарка medium) на гриле, картофельная булочка на гриле, фирменный соус, лист салата, томат, маринованный лук, жареный бекон, сыр чеддер.",
            "https://www.delivery-club.ru/media/cms/relation_product/32350/312372888_m650.jpg",
            259
        ),
        Dish(
            "5ed8da011f071c00465b2027",
            "Бургер \"Мексика\"",
            "295 г • Котлета из 100% говядины (прожарка medium) на гриле, картофельная булочка на гриле, мексиканские чипсы начос, лист салата, перчики халапеньо, сыр чеддер, соус сальса из свежих томатов.",
            "https://www.delivery-club.ru/media/cms/relation_product/32350/312372889_m650.jpg",
            229
        ),
        Dish(
            "5ed8da011f071c00465b2028",
            "Бургер \"Русский\"",
            "460 г • Две котлеты из 100% говядины (прожарка medium) на гриле, картофельная булочка на гриле, фирменный соус, лист салата, томат, маринованный лук, маринованные огурчики, двойной сыр чеддер, соус ранч.",
            "https://www.delivery-club.ru/media/cms/relation_product/32350/312372890_m650.jpg",
            379
        ),
        Dish(
            "5ed8da011f071c00465b2029",
            "Бургер \"Люксембург\"",
            "Куриное филе приготовленное на гриле, картофельная булочка на гриле, сыр чеддер, соус ранч, лист салата, томат, свежий огурец.",
            "https://www.delivery-club.ru/media/cms/relation_product/32350/312372891_m650.jpg",
            189
        ),
        Dish(
            "5ed8da011f071c00465b202a",
            "Бургер \"Классика\"",
            "290 г • Котлета из 100% говядины (прожарка medium) на гриле, картофельная булочка на гриле, фирменный соус, лист салата, томат, сыр чеддер.",
            "https://www.delivery-club.ru/media/cms/relation_product/32350/312372893_m650.jpg",
            199
        ),
        Dish(
            "5ed8da011f071c00465b202b",
            "Бургер \"Швейцария\"",
            "320 г • Котлета из 100% говядины (прожарка medium) на гриле, картофельная булочка на гриле, натуральный сырный соус, лист салата, томат, маринованный огурчик, 2 ломтика сыра чеддер.",
            "https://www.delivery-club.ru/media/cms/relation_product/32350/312700349_m650.jpg",
            279
        ),
        Dish(
            "5ed8da011f071c00465b202e",
            "Пицца Дьябло с двойной начинкой",
            "Бекон, свинина, пепперони, перец болгарский, халапеньо, томатный пицца-соус, соус шрирача, моцарелла, зелень",
            "https://www.delivery-club.ru/media/cms/relation_product/13219/301422298_m650.jpg",
            779
        ),
        Dish(
            "5ed8da011f071c00465b202f",
            "Пицца Карбонара с двойной начинкой",
            "Бекон, пармезан, соус сливочный, моцарелла",
            "https://www.delivery-club.ru/media/cms/relation_product/13219/301422305_m650.jpg",
            739
        ),
        Dish(
            "5ed8da011f071c00465b2030",
            "Пицца Петровская с двойной начинкой",
            "Пепперони, курица, бекон, ветчина, помидоры, шампиньоны, лук красный, моцарелла, укроп",
            "https://www.delivery-club.ru/media/cms/relation_product/13219/301422296_m650.jpg",
            895
        ),
        Dish(
            "5ed8da011f071c00465b2031",
            "Пицца 2 берега с двойной начинкой",
            "Свинина, курица, пепперони, ветчина, бекон, помидоры, перец болгарский, соус барбекю, моцарелла,укроп",
            "https://www.delivery-club.ru/media/cms/relation_product/13219/301422299_m650.jpg",
            899
        ),
        Dish(
            "5ed8da011f071c00465b2032",
            "Пицца Мясная с двойной начинкой",
            "Охотничьи колбаски, курица, свинина, пепперони, шампиньоны, томатный пицца-соус, моцарелла, зелень",
            "https://www.delivery-club.ru/media/cms/relation_product/13219/301422306_m650.jpg",
            895
        ),
        Dish(
            "5ed8da011f071c00465b2033",
            "Пицца Маргарита с двойной начинкой",
            "Моцарелла, помидоры, томатный пицца-соус",
            "https://www.delivery-club.ru/media/cms/relation_product/13219/301422261_m650.jpg",
            524
        ),
    )
}