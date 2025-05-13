package com.example.demo.databaseInitializer;


import com.example.demo.dto.basket.BasketRequest;
import com.example.demo.dto.basketItem.BasketItemRequest;
import com.example.demo.dto.item.ItemRequest;
import com.example.demo.dto.itemDiscount.ItemDiscountRequest;
import com.example.demo.dto.priceAlert.PriceAlertRequest;
import com.example.demo.dto.purchasedItem.PurchasedItemRequest;
import com.example.demo.dto.spending.SpendingRequest;
import com.example.demo.dto.store.StoreRequest;
import com.example.demo.dto.storeItem.StoreItemRequest;
import com.example.demo.model.enums.Category;
import com.example.demo.model.enums.Currency;
import com.example.demo.model.enums.UnitType;
import com.example.demo.service.*;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class InsertDummyDataInDB {

    @Autowired
    private ItemService itemService;

    @Autowired
    private StoreService storeService;

    @Autowired
    private StoreItemService storeItemService;

    @Autowired
    private BasketService basketService;

    @Autowired
    private BasketItemService basketItemService;

    @Autowired
    private ItemDiscountService itemDiscountService;

    @Autowired
    private PriceAlertService priceAlertService;

    @Autowired
    private SpendingService spendingService;

    @Autowired
    private PurchasedItemService purchasedItemService;


    @Transactional
    public void populateDatabase() {
        for(int initializeDBCounter=1;initializeDBCounter<=8;initializeDBCounter++) {

            // 1. Create items (30 entries)
            if(initializeDBCounter == 1 ) createItems();

            // 2. Create stores (3 entries)
            if(initializeDBCounter == 2 ) createStores();

            // 3. Create store items (76 entries)
            if(initializeDBCounter == 3 ) createStoreItems();

            // 4. Create baskets (3 entries)
            if(initializeDBCounter == 4 ) createBaskets();

            // 5. Create basket items (16 entries)
            if(initializeDBCounter == 5 ) createBasketItems();

            // 6. Create item discounts (3 entries)
            if(initializeDBCounter == 6 ) createItemDiscounts();

            // 7. Create price alerts (3 entries)
            if(initializeDBCounter == 7 ) createPriceAlerts();

            // 8. Create spending and purchased items
            if(initializeDBCounter == 8 ) createSpendingsAndPurchasedItems();
        }
    }

    private void createItems() {
        List<ItemRequest> items = List.of(
                new ItemRequest("lapte zuzu", Category.LACTATE, "Zuzu", UnitType.L),
                new ItemRequest("iaurt grecesc", Category.LACTATE, "Proxi", UnitType.KG),
                new ItemRequest("oua marimea M", Category.OUA, "facute de cocos", UnitType.BUC),
                new ItemRequest("branza telemea", Category.LACTATE, "Proxi", UnitType.KG),
                new ItemRequest("paine alba", Category.PANIFICATIE, "Proxi", UnitType.G),
                new ItemRequest("rosii cherry", Category.LEGUME_SI_FRUCTE, "Generic", UnitType.G),
                new ItemRequest("piept pui", Category.CARNE, "Avicola", UnitType.KG),
                new ItemRequest("spaghetti", Category.PASTE_FAINOASE, "Barilla", UnitType.G),
                new ItemRequest("zahar tos", Category.ALIMENTE_DE_BAZA, "Proxi", UnitType.KG),
                new ItemRequest("apa plata", Category.BAUTURI, "Aqua Carpatica", UnitType.L),
                new ItemRequest("banane", Category.LEGUME_SI_FRUCTE, "Generic", UnitType.KG),
                new ItemRequest("ulei floarea-soarelui", Category.ALIMENTE_DE_BAZA, "Spornic", UnitType.L),
                new ItemRequest("biscuiti cu unt", Category.GUSTARI, "RoStar", UnitType.KG),
                new ItemRequest("cafea macinata", Category.CAFEA, "Tchibo", UnitType.KG),
                new ItemRequest("detergent lichid", Category.PRODUSE_DE_MENAJ, "Dero", UnitType.L),
                new ItemRequest("sampon par gras", Category.INGRIJIRE_PERSONALA, "Schauma", UnitType.ML),
                new ItemRequest("hartie igienica", Category.PRODUSE_DE_MENAJ, "Motto", UnitType.ROLE),
                new ItemRequest("piper negru macinat", Category.CONDIMENTE, "Kamis", UnitType.G),
                new ItemRequest("vin alb demisec", Category.BAUTURI, "Cotnari", UnitType.L),
                new ItemRequest("ciocolata neagra 70%", Category.GUSTARI, "Poiana", UnitType.G),
                new ItemRequest("suc pepsi", Category.BAUTURI, "Pepsi", UnitType.L),
                new ItemRequest("biscuiti oreo", Category.GUSTARI, "Oreo", UnitType.G),
                new ItemRequest("ceapa galbena", Category.LEGUME_SI_FRUCTE, "Generic", UnitType.KG),
                new ItemRequest("cartofi albi", Category.LEGUME_SI_FRUCTE, "Generic", UnitType.KG),
                new ItemRequest("file somon", Category.PESTE, "Lidl", UnitType.KG),
                new ItemRequest("crema de branza", Category.LACTATE, "Almette", UnitType.KG),
                new ItemRequest("malai extra", Category.ALIMENTE_DE_BAZA, "Pambac", UnitType.KG),
                new ItemRequest("orez bob lung", Category.ALIMENTE_DE_BAZA, "Deroni", UnitType.KG),
                new ItemRequest("pasta de dinti", Category.INGRIJIRE_PERSONALA, "Colgate", UnitType.ML),
                new ItemRequest("morcovi", Category.LEGUME_SI_FRUCTE, "Generic", UnitType.KG)
        );
        items.forEach(itemService::createItem);
    }

    private void createStores() {
        List<StoreRequest> stores = List.of(
                new StoreRequest("Lidl", List.of(), List.of(), List.of()),
                new StoreRequest("Profi", List.of(), List.of(), List.of()),
                new StoreRequest("Kaufland", List.of(), List.of(), List.of())
        );
        stores.forEach(storeService::createStore);
    }

    private void createStoreItems() {
        List<StoreItemRequest> storeItems = List.of(
                // Lidl (store_id=1)
                new StoreItemRequest(1L, 1L, 9.90f, 1, Currency.RON),
                new StoreItemRequest(1L, 2L, 11.50f, 0.4f, Currency.RON),
                new StoreItemRequest(1L, 3L, 13.20f, 10, Currency.RON),
                new StoreItemRequest(1L, 4L, 12.80f, 0.3f, Currency.RON),
                new StoreItemRequest(1L, 5L, 3.50f, 500, Currency.RON),
                new StoreItemRequest(1L, 6L, 6.80f, 250, Currency.RON),
                new StoreItemRequest(1L, 7L, 28.50f, 1, Currency.RON),
                new StoreItemRequest(1L, 8L, 5.80f, 500, Currency.RON),
                new StoreItemRequest(1L, 9L, 4.40f, 1, Currency.RON),
                new StoreItemRequest(1L, 10L, 5.20f, 2, Currency.RON),
                new StoreItemRequest(1L, 11L, 6.10f, 1, Currency.RON),
                new StoreItemRequest(1L, 12L, 9.20f, 1, Currency.RON),
                new StoreItemRequest(1L, 13L, 7.10f, 0.2f, Currency.RON),
                new StoreItemRequest(1L, 14L, 22.40f, 0.25f, Currency.RON),
                new StoreItemRequest(1L, 15L, 49.90f, 2.5f, Currency.RON),
                new StoreItemRequest(1L, 16L, 17.80f, 400, Currency.RON),
                new StoreItemRequest(1L, 17L, 18.90f, 10, Currency.RON),
                new StoreItemRequest(1L, 18L, 6.00f, 50, Currency.RON),
                new StoreItemRequest(1L, 19L, 23.50f, 0.75f, Currency.RON),
                new StoreItemRequest(1L, 20L, 3.90f, 100, Currency.RON),
                new StoreItemRequest(1L, 21L, 13.99f, 2.5f, Currency.RON),
                new StoreItemRequest(1L, 22L, 3.49f, 1, Currency.RON),
                new StoreItemRequest(1L, 23L, 2.95f, 1, Currency.RON),
                new StoreItemRequest(1L, 24L, 3.10f, 1, Currency.RON),
                new StoreItemRequest(1L, 25L, 22.00f, 0.2f, Currency.RON),

                // Profi (store_id=2)
                new StoreItemRequest(2L, 1L, 12.90f, 1, Currency.RON),
                new StoreItemRequest(2L, 2L, 11.50f, 0.4f, Currency.RON),
                new StoreItemRequest(2L, 3L, 12.20f, 10, Currency.RON),
                new StoreItemRequest(2L, 4L, 13.10f, 0.3f, Currency.RON),
                new StoreItemRequest(2L, 5L, 3.50f, 500, Currency.RON),
                new StoreItemRequest(2L, 6L, 8.80f, 250, Currency.RON),
                new StoreItemRequest(2L, 7L, 26.50f, 1, Currency.RON),
                new StoreItemRequest(2L, 8L, 5.80f, 500, Currency.RON),
                new StoreItemRequest(2L, 9L, 4.40f, 1, Currency.RON),
                new StoreItemRequest(2L, 10L, 5.20f, 2, Currency.RON),
                new StoreItemRequest(2L, 11L, 6.10f, 1, Currency.RON),
                new StoreItemRequest(2L, 12L, 9.20f, 1, Currency.RON),
                new StoreItemRequest(2L, 13L, 7.10f, 0.2f, Currency.RON),
                new StoreItemRequest(2L, 14L, 22.40f, 0.25f, Currency.RON),
                new StoreItemRequest(2L, 15L, 49.90f, 2.5f, Currency.RON),
                new StoreItemRequest(2L, 16L, 17.80f, 400, Currency.RON),
                new StoreItemRequest(2L, 17L, 18.90f, 10, Currency.RON),
                new StoreItemRequest(2L, 18L, 6.00f, 50, Currency.RON),
                new StoreItemRequest(2L, 19L, 23.50f, 0.75f, Currency.RON),
                new StoreItemRequest(2L, 20L, 3.90f, 100, Currency.RON),
                new StoreItemRequest(2L, 21L, 14.50f, 2.5f, Currency.RON),

                // Kaufland (store_id=3)
                new StoreItemRequest(3L, 1L, 10.10f, 1, Currency.RON),
                new StoreItemRequest(3L, 2L, 11.80f, 0.4f, Currency.RON),
                new StoreItemRequest(3L, 3L, 13.50f, 10, Currency.RON),
                new StoreItemRequest(3L, 4L, 13.10f, 0.3f, Currency.RON),
                new StoreItemRequest(3L, 5L, 3.60f, 500, Currency.RON),
                new StoreItemRequest(3L, 6L, 7.00f, 250, Currency.RON),
                new StoreItemRequest(3L, 7L, 27.90f, 1, Currency.RON),
                new StoreItemRequest(3L, 8L, 5.90f, 500, Currency.RON),
                new StoreItemRequest(3L, 9L, 4.50f, 1, Currency.RON),
                new StoreItemRequest(3L, 10L, 5.30f, 2, Currency.RON),
                new StoreItemRequest(3L, 11L, 6.20f, 1, Currency.RON),
                new StoreItemRequest(3L, 12L, 9.50f, 1, Currency.RON),
                new StoreItemRequest(3L, 13L, 7.50f, 0.2f, Currency.RON),
                new StoreItemRequest(3L, 14L, 23.00f, 0.25f, Currency.RON),
                new StoreItemRequest(3L, 15L, 50.50f, 2.5f, Currency.RON),
                new StoreItemRequest(3L, 16L, 18.00f, 400, Currency.RON),
                new StoreItemRequest(3L, 17L, 19.20f, 10, Currency.RON),
                new StoreItemRequest(3L, 18L, 6.10f, 50, Currency.RON),
                new StoreItemRequest(3L, 19L, 24.00f, 0.75f, Currency.RON),
                new StoreItemRequest(3L, 20L, 4.10f, 100, Currency.RON),
                new StoreItemRequest(3L, 21L, 13.99f, 2.5f, Currency.RON),
                new StoreItemRequest(3L, 22L, 3.99f, 1, Currency.RON),
                new StoreItemRequest(3L, 23L, 3.10f, 1, Currency.RON),
                new StoreItemRequest(3L, 24L, 2.99f, 1, Currency.RON),
                new StoreItemRequest(3L, 25L, 24.50f, 0.2f, Currency.RON),
                new StoreItemRequest(3L, 26L, 8.50f, 0.15f, Currency.RON),
                new StoreItemRequest(3L, 27L, 5.50f, 1, Currency.RON),
                new StoreItemRequest(3L, 28L, 8.00f, 1, Currency.RON),
                new StoreItemRequest(3L, 29L, 10.50f, 100, Currency.RON),
                new StoreItemRequest(3L, 30L, 2.60f, 0.5f, Currency.RON)
        );
        storeItems.forEach(storeItemService::createStoreItem);
    }

    private void createBaskets() {
        List<BasketRequest> baskets = List.of(
                new BasketRequest(1L, 1L, List.of()),
                new BasketRequest(1L, 2L, List.of()),
                new BasketRequest(1L, 3L, List.of())
        );
        baskets.forEach(basketService::createBasket);
    }

    private void createBasketItems() {
        List<BasketItemRequest> basketItems = List.of(
                // Lidl Basket (basket_id=1)
                new BasketItemRequest(1L, 1L, 1),
                new BasketItemRequest(1L, 2L, 1),
                new BasketItemRequest(1L, 3L, 1),
                new BasketItemRequest(1L, 4L, 1),
                new BasketItemRequest(1L, 5L, 1),

                // Profi Basket (basket_id=2)
                new BasketItemRequest(2L, 26L, 1),
                new BasketItemRequest(2L, 28L, 1),
                new BasketItemRequest(2L, 30L, 1),
                new BasketItemRequest(2L, 32L, 1),
                new BasketItemRequest(2L, 34L, 1),
                new BasketItemRequest(2L, 36L, 1),
                new BasketItemRequest(2L, 38L, 1),

                // Kaufland Basket (basket_id=3)
                new BasketItemRequest(3L, 50L, 1),
                new BasketItemRequest(3L, 51L, 1),
                new BasketItemRequest(3L, 52L, 1),
                new BasketItemRequest(3L, 53L, 1)
        );
        basketItems.forEach(basketItemService::addItemToBasket);
    }

    private void createItemDiscounts() {
        LocalDateTime start1 = LocalDateTime.of(2025, 5, 10, 8, 30);
        LocalDateTime end1 = LocalDateTime.of(2025, 5, 17, 8, 30);
        itemDiscountService.createDiscount(new ItemDiscountRequest(19L, 1L, 23.50f, 10, start1, end1));

        LocalDateTime start2 = LocalDateTime.of(2025, 5, 12, 8, 0);
        LocalDateTime end2 = LocalDateTime.of(2025, 5, 19, 8, 30);
        itemDiscountService.createDiscount(new ItemDiscountRequest(20L, 2L, 3.90f, 5, start2, end2));

        LocalDateTime start3 = LocalDateTime.of(2025, 5, 15, 9, 30);
        LocalDateTime end3 = LocalDateTime.of(2025, 5, 22, 9, 30);
        itemDiscountService.createDiscount(new ItemDiscountRequest(19L, 3L, 24.00f, 15, start3, end3));
    }

    private void createPriceAlerts() {
        priceAlertService.createAlert(new PriceAlertRequest(1L, 7L, 28.00f));
        priceAlertService.createAlert(new PriceAlertRequest(1L, 21L, 12.50f));
        priceAlertService.createAlert(new PriceAlertRequest(1L, 25L, 22.00f));
    }

    private void createSpendingsAndPurchasedItems() {
        SpendingRequest spendingRequest = new SpendingRequest(
                1L,
                3L,
                List.of(
                        new PurchasedItemRequest(1L,"suc pepsi", 13.99f, 1, UnitType.L),
                        new PurchasedItemRequest(1L,"lapte zuzu", 10.10f, 1, UnitType.L),
                        new PurchasedItemRequest(1L,"piept pui", 27.90f, 1, UnitType.KG),
                        new PurchasedItemRequest(1L,"banane", 6.20f, 1, UnitType.KG)
                ),
                58.19f,
                LocalDateTime.of(2025, 5, 1, 11, 37, 26)
        );
        spendingService.createSpending(spendingRequest);
    }
}