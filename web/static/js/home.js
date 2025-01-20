document.addEventListener('DOMContentLoaded', () => {
    const renderBlogList = (blogs) => {
        const blogList = document.getElementById('blog-list');
        blogList.innerHTML = blogs.map(item => `
                    <div class="blog-item"> 
                        <div class="header">
                            <img src="../static/img/null_avatar.png" alt="头像" class="avatar">
                            <span class="username">${item.username}</span>
                            <span class="timestamp">${item.postTime}</span>
                        </div>
                        <div class="blog-item-container">
                            <div class="title">${item.title}</div>
                            <hr class="line">
                            <div class="content">${item.content}</div>
                            ${item.imageURL ? `<img src="../${item.imageURL}" alt="内容图片" class="image">` : ''}
                        </div>
                    </div>
                `).join('');
    };

    const renderPagination = (total, currentPage) => {
        const pageSize = 5;
        const totalPages = Math.ceil(total / pageSize);
        const pagination = document.querySelector('.pagination');
        pagination.innerHTML = Array.from({ length: totalPages }, (_, i) => {
            let page = i + 1;
            return `<button class="${page === currentPage ? 'active' : ''}" onclick="fetchBlogs(${page})">${page}</button>`;
        }).join('');
    };

    window.fetchBlogs = (page = 1) => {
        const pageSize = 5;
        fetch(`../BlogServlet?page=${page}&size=${pageSize}`)
            .then(response => response.json())
            .then(data => {
                renderBlogList(data.blogList);
                renderPagination(data.total, page);
            })
            .catch(error => console.error('Error fetching blogs:', error));
    };

    document.getElementById('blog-form').addEventListener('submit', (event) => {
        event.preventDefault();
        const formData = new FormData(event.target);
        fetch('../BlogServlet', {
            method: 'POST',
            body: formData
        })
            .then(response => response.json())
            .then(result => {
                if (result.success) {
                    alert(result.message);
                    fetchBlogs(1);
                } else {
                    alert('添加失败: ' + result.message);
                }
            })
            .catch(error => console.error('Error adding blog:', error));
    });

    let slides;
    let currentIndex = 0;
    let intervalId;
    window.fetchSlideData = () => {
        fetch('../SlideServlet')
            .then(response => response.json())
            .then(data => {
                if (data.length !== 0) {
                    slides = data;
                    renderSlide();
                    startSlide();
                    updateSlidePosition();
                }
            })
            .catch(error => console.error('Error fetching slide data:', error));
    };

    const renderSlide = ()=> {
        const slideList = document.querySelector('.slide-list');
        slideList.innerHTML = '';

        slides.forEach(item => {
            const div = document.createElement('div');
            div.classList.add('slide-item');

            const img = document.createElement('img');
            if (item.imageURL != null) {
                img.src = `../${item.imageURL}`;
            } else {
                img.src = '../static/img/null_slide_image.jpeg';
            }
            div.appendChild(img);

            slideList.appendChild(div);
        });

        const firstItem = slides[0];
        const lastItem = slides[slides.length - 1];
        const cloneFirstItem = document.createElement('div');
        cloneFirstItem.classList.add('slide-item');
        cloneFirstItem.innerHTML = `<img src="../${firstItem.imageURL}" alt="">`;

        const cloneLastItem = document.createElement('div');
        cloneLastItem.classList.add('slide-item');
        cloneLastItem.innerHTML = `<img src="../${lastItem.imageURL}" alt="">`;

        slideList.appendChild(cloneFirstItem);
        slideList.insertBefore(cloneLastItem, slideList.firstChild);
    };
    const startSlide = ()=> {
        updateSlidePosition();
        intervalId = setInterval(() => {
            currentIndex++;
            if (currentIndex >= slides.length) {
                currentIndex = 0;
            }
            updateSlidePosition();
        }, 5000);
    };
    const stopSlide = () =>  {
        clearInterval(intervalId);
    }

    const updateSlidePosition = () => {
        const slideList = document.querySelector('.slide-list');
        const description = document.querySelector('.description');
        const offset = -((currentIndex + 1) * 100);
        description.innerHTML = `${slides[currentIndex].title}`;
        slideList.style.transform = `translateX(${offset}%)`;
    };

    window.preSlide = () => {
        -- currentIndex;
        if (currentIndex < 0) {
            currentIndex = 0;
        }
        updateSlidePosition();
    }
    window.nextSlide = () => {
        ++ currentIndex;
        if (currentIndex >= slides.length) {
            currentIndex = 0;
        }
        updateSlidePosition();
    }

    fetchBlogs();
    fetchSlideData();
    startSlide();

    document.querySelector('.slide-list').addEventListener('mouseenter', startSlide);
    document.querySelector('.slide-list').addEventListener('mouseleave', stopSlide);

    console.log(
        "Ciallo～(∠・ω< )⌒★\n\n" +
        "    | $$$    /$$$                                  | $$      | $$\n" +
        "    | $$$$  /$$$$  /$$$$$$  /$$$$$$/$$$$   /$$$$$$ | $$$$$$$ | $$  /$$$$$$   /$$$$$$\n" +
        "    | $$ $$/$$ $$ /$$__  $$| $$_  $$_  $$ /$$__  $$| $$__  $$| $$ /$$__  $$ /$$__  $$\n" +
        "    | $$  $$$| $$| $$  \\ $$| $$ \\ $$ \\ $$| $$  \\ $$| $$  \\ $$| $$| $$  \\ $$| $$  \\ $$\n" +
        "    | $$\\  $ | $$| $$  | $$| $$ | $$ | $$| $$  | $$| $$  | $$| $$| $$  | $$| $$  | $$\n" +
        "    | $$ \\/  | $$|  $$$$$$/| $$ | $$ | $$|  $$$$$$/| $$$$$$$/| $$|  $$$$$$/|  $$$$$$$\n" +
        "    |__/     |__/ \\______/ |__/ |__/ |__/ \\______/ |_______/ |__/ \\______/  \\____  $$\n" +
        "                                                                            /$$  \\ $$\n" +
        "                                                                           |  $$$$$$/\n" +
        "                                                                            \\______/ \n");
});