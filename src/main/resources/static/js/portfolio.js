document.addEventListener("DOMContentLoaded", () => {

    console.log("Portfolio JS Loaded 🚀");
    // Change header color when button is clicked
    const changeBtn = document.getElementById("changeColorBtn");
    if (changeBtn) {
        var flag = true;
        var gradient = "";
        changeBtn.addEventListener("click", () => {
            if (flag) {
                gradient = "linear-gradient(135deg, #ff5722, #e91e63)";
                flag = false;
            } else {
                gradient = "linear-gradient(135deg, #1e90ff, #007acc)";
                flag = true;
            }
            document.querySelector("header").style.background = gradient;
            console.log("Header color changed! 🎨");
        });
    }
});


