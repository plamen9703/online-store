async function updateCart(productId, quantity) {

    const response = await fetch("/cart/update", {
        method: "POST",
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify({
            productId: productId,
            quantity: quantity
        })
    });

    if (!response.ok) {

        const errorMessage = await response.text();

        alert(errorMessage);

        throw new Error(errorMessage);
    }
}

async function increaseQuantity(button) {

    const productId = button.dataset.productId;

    const quantityElement =
        document.getElementById("quantity-" + productId);

    let quantity = parseInt(quantityElement.innerText);

    let newQuantity = quantity + 1;

    try {

        await updateCart(productId, newQuantity);

        quantityElement.innerText = newQuantity;

        await updatePrice();

    } catch (error) {

        console.error(error);
    }
}

async function decreaseQuantity(button) {

    const productId = button.dataset.productId;

    const quantityElement =
        document.getElementById("quantity-" + productId);

    let quantity = parseInt(quantityElement.innerText);

    if (quantity <= 1){
        await removeProduct(button)
        return;
    }

    let newQuantity = quantity - 1;

    try {

        await updateCart(productId, newQuantity);

        quantityElement.innerText = newQuantity;

        await updatePrice();

    } catch (error) {

        console.error(error);
    }
}

async function removeProduct(button) {

    const productId = button.dataset.productId;

    await updateCart(productId, 0);

    document.getElementById("item-" + productId).remove();
}

async function addToCart(button) {

    const productId = button.dataset.productId;

    const response = await fetch("/cart/add", {
        method: "POST",
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify({
            productId: productId
        })
    });

    if (!response.ok) {

        const errorMessage = await response.text();

        alert(errorMessage);

        return;
    }

    button.innerText = "Added ✓";

    setTimeout(() => {
        button.innerText = "Add to Cart";
    }, 1500);
}

async function updatePrice() {

    const priceElement = document.getElementById("totalPrice");

    const response = await fetch("/cart/totalprice");

    const total = await response.text();

    priceElement.innerText = total;
}