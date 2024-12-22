document.addEventListener("DOMContentLoaded", () => {
  const themeToggleButton = document.getElementById("theme-toggle");
  const body = document.body;

  // Load theme from localStorage
  const savedTheme = localStorage.getItem("theme");
  if (savedTheme) {
    body.classList.add(savedTheme);
  }

  themeToggleButton.addEventListener("click", () => {
    if (body.classList.contains("dark")) {
      body.classList.remove("dark");
      localStorage.setItem("theme", "");
    } else {
      body.classList.add("dark");
      localStorage.setItem("theme", "dark");
    }
  });
});
