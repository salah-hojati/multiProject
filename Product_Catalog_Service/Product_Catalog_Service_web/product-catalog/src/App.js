import React, { useState, useEffect } from "react";

function App() {
    const [products, setProducts] = useState([]);
    const [newProduct, setNewProduct] = useState({
        name: "",
        description: "",
        price: ""
    });

    // گرفتن محصولات از سرور
    useEffect(() => {
        fetch("http://localhost:8080/api/products")
            .then((response) => response.json())
            .then((data) => {
                console.log(data); // بررسی داده‌ها
                setProducts(data); // تنظیم داده‌ها در state
            })
            .catch((error) => console.error('Error:', error)); // مدیریت خطا
    }, []);

    // تغییر state برای ورودی‌ها
    const handleChange = (e) => {
        const { name, value } = e.target;
        setNewProduct((prevProduct) => ({
            ...prevProduct,
            [name]: value,
        }));
    };

    // ارسال محصول جدید به سرور
    const handleSubmit = (e) => {
        e.preventDefault();

        // ارسال درخواست POST برای افزودن محصول جدید
        fetch("http://localhost:8080/api/products", {
            method: "POST",
            headers: {
                "Content-Type": "application/json",
            },
            body: JSON.stringify(newProduct),
        })
            .then((response) => response.json())
            .then((data) => {
                console.log("Product added:", data);
                // پس از افزودن محصول جدید، لیست محصولات را به روز می‌کنیم
                setProducts((prevProducts) => [...prevProducts, data]);
                // پاک کردن فیلدهای فرم
                setNewProduct({
                    name: "",
                    description: "",
                    price: "",
                });
            })
            .catch((error) => console.error('Error:', error)); // مدیریت خطا
    };

    return (
        <div>
            <h1>Product Catalog</h1>

            {/* فرم افزودن محصول */}
            <h2>Add New Product</h2>
            <form onSubmit={handleSubmit}>
                <div>
                    <label>Product Name</label>
                    <input
                        type="text"
                        name="name"
                        value={newProduct.name}
                        onChange={handleChange}
                    />
                </div>
                <div>
                    <label>Description</label>
                    <input
                        type="text"
                        name="description"
                        value={newProduct.description}
                        onChange={handleChange}
                    />
                </div>
                <div>
                    <label>Price</label>
                    <input
                        type="number"
                        name="price"
                        value={newProduct.price}
                        onChange={handleChange}
                    />
                </div>
                <button type="submit">Add Product</button>
            </form>

            {/* نمایش محصولات */}
            <h2>Products</h2>
            <ul>
                {products.map((product) => (
                    <li key={product.id}>
                        {product.name}: {product.description} - ${product.price}
                    </li>
                ))}
            </ul>
        </div>
    );
}

export default App;
