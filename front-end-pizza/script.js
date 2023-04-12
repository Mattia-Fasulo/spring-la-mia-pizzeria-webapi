//-------------GLOBAL VARIABLES-----------// 

const PIZZA_URL = 'http://localhost:8080/api/pizzas';
const contentElement = document.getElementById('content');
const pizzaForm = document.getElementById('pizza-form');
const toggleForm = document.getElementById('toggle-form');


//------------------API------------------//

const getPizzaList = async ()=>{
    const response = await fetch(PIZZA_URL);
    return response;
}

const postPizza = async (jsonData) => {
    const fetchOptions = {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
      },
      body: jsonData,
    };
    const response = await fetch(PIZZA_URL, fetchOptions);
    return response;
  };


//-----------DOM MANIPULATION----------//

const toggleFormVisibility = () => {
    document.getElementById('form').classList.toggle('d-none');
  };

const createIngredientlist = (ingredients)=>{
    let ingredientsHtml = `<p>`;

    ingredients.forEach((element)=>{
        ingredientsHtml += `<span class="mx-1 badge bg-secondary">${element.name}</span>`;
    })

    ingredientsHtml += `</p>`;
    return ingredientsHtml;
}

const createPizzaItem = (item)=>{
    return `<div class="col-4 ">
                <div class="card h-100">
                    <img style="height: 265px;" src="${item.imgPath}" class="card-img-top" alt="${item.name}">

                    <div class="card-body">
                        <h5 class="card-title">${item.name}</h5>

                        <p class="card-text">${item.description}</p>

                        <p class="card-text">${item.price} €</p>

                        <a href="#" class="btn btn-secondary">Go somewhere</a>
                    </div>
                    <div class="card-footer">
                    ${createIngredientlist(item.ingredients)}
                    </div>
                </div>
            </div>
    `
}

const createPizzaList = (data)=>{
    let list = `<div class="row gy-3">`

    data.forEach(element => {
        list += createPizzaItem(element);
    });

    list += `</div>`;

    return list;
}






//-----------OTHER FUNCTION-------------//
const loadData = async ()=>{

    const response = await getPizzaList();
    response.json().then((data)=>{

        console.log(data);
        contentElement.innerHTML = createPizzaList(data);

    });   

}


const savePizza = async (event)=>{
    event.preventDefault();

    // console.log(event.target)
    
    const formData = new FormData(event.target);

    const formDataObj = Object.fromEntries(formData.entries());
    // console.log(formDataObj);

    const formDataJson = JSON.stringify(formDataObj);
    // console.log(formDataJson);

    const response = await postPizza(formDataJson);

    const responseBody = await response.json();

    if (response.ok) {
        // reload data
        loadData();
        // reset form
        event.target.reset();
      } else {
        // handle error
        console.log('ERROR');
        console.log(response.status);
        console.log(responseBody);
      }
    
}

loadData();
pizzaForm.addEventListener('submit', savePizza);
toggleForm.addEventListener('click', toggleFormVisibility);

