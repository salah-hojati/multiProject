برای افزودن یک محصول جدید در پروژه‌تان، باید یک فرم ورودی برای دریافت اطلاعات محصول از کاربر طراحی کنید و سپس این اطلاعات را از طریق درخواست POST به سرور ارسال کنید. در اینجا مراحل کامل برای این کار را توضیح می‌دهم.
1. ایجاد فرم برای افزودن محصول

در ابتدا باید یک فرم برای وارد کردن اطلاعات محصول ایجاد کنید. این فرم شامل فیلدهایی مانند name, description, price خواهد بود. سپس اطلاعات ورودی‌ها را در state ذخیره می‌کنیم و پس از ارسال درخواست به سرور، محصول جدید به لیست محصولات اضافه می‌شود.
2. کد کامل با اضافه کردن محصول

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

توضیحات:

    State برای محصول جدید: در ابتدا یک state جدید برای ذخیره اطلاعات محصول جدید تعریف کردیم به نام newProduct:

const [newProduct, setNewProduct] = useState({
name: "",
description: "",
price: ""
});

این state ذخیره‌کننده اطلاعات فرم است.

مدیریت ورودی‌ها: برای هر فیلد در فرم یک input داریم که در آن از onChange استفاده می‌کنیم تا مقدار فیلدها را به روز کنیم:

const handleChange = (e) => {
const { name, value } = e.target;
setNewProduct((prevProduct) => ({
...prevProduct,
[name]: value,
}));
};

ارسال محصول جدید به سرور: وقتی که کاربر فرم را ارسال می‌کند، اطلاعات فرم به سرور از طریق یک درخواست POST ارسال می‌شود:

    const handleSubmit = (e) => {
      e.preventDefault();
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
          // افزودن محصول جدید به لیست
          setProducts((prevProducts) => [...prevProducts, data]);
          // پاک کردن فرم
          setNewProduct({
            name: "",
            description: "",
            price: "",
          });
        })
        .catch((error) => console.error('Error:', error)); // مدیریت خطا
    };

    نمایش محصولات: در نهایت، محصولات موجود در state products نمایش داده می‌شود. برای هر محصول، یک آیتم لیست با استفاده از map ساخته می‌شود.

5. نکات مهم:

   پاک کردن فرم: پس از ارسال محصول جدید به سرور، فیلدهای فرم به صورت خودکار پاک می‌شوند.
   ارسال به سرور: شما از fetch برای ارسال درخواست POST استفاده کرده‌اید. این درخواست به سرور محصول جدید را می‌فرستد و پس از افزودن محصول جدید، لیست محصولات به‌روزرسانی می‌شود.

