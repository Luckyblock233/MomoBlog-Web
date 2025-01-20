document.addEventListener('DOMContentLoaded', () => {
    let inSlide;

    const renderBlogList = (blogs) => {
        const blogList = document.getElementById('blog-list');
        blogList.innerHTML = blogs.map(item => `
                    <div class="blog-item"> 
                        <div class="header">
                            <img src="../uploads/avatars/null_avatar.png" alt="头像" class="avatar">
                            <span class="username">${item.username}</span>
                            <span class="timestamp">${item.postTime}</span>
                        </div>
                        <div class="title">${item.title}</div>
                        <hr class="line"></hr>
                        <div class="content">${item.content}</div>
                        ${item.imageURL ? `<img src="../${item.imageURL}" alt="内容图片" class="image">` : ''}
                        <button onclick="deleteBlog(${item.id})">删除</button>
                        <button onclick="flipSetSlide(${item.id})"> ${inSlide.has(item.id) ? "取消推荐" : "设为推荐"}</button>
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
        fetch(`../SlideServlet`)
            .then(response => response.json())
            .then(data => {
                inSlide = new Set();
                data.map(item => {
                    inSlide.add(item.blogId);
                });
            })
            .catch(error => console.error('Error fetching slides:', error));

        fetch(`../BlogServlet?page=${page}&size=${pageSize}`)
            .then(response => response.json())
            .then(data => {
                blogs = data;
                renderBlogList(data.blogList);
                renderPagination(data.total, page);
            })
            .catch(error => console.error('Error fetching blogs:', error));
    };

    window.deleteBlog = (id) => {
        fetch(`../BlogServlet/${id}`, { method: 'DELETE' })
            .then(response => response.json())
            .then(result => {
                if (result.success) {
                    alert(result.message);
                    fetchBlogs(1);
                } else {
                    alert('删除失败: ' + result.message);
                }
            })
            .catch(error => console.error('Error deleting blog:', error));
    };

    window.flipSetSlide = (id) => {
        if (inSlide.has(id)) {
            fetch(`../SlideServlet/${id}`, { method: 'DELETE' })
                .then(response => response.json())
                .then(result => {
                    if (result.success) {
                        alert(result.message);
                        fetchBlogs(1);
                    } else {
                        alert('取消推荐失败: ' + result.message);
                    }
                })
                .catch(error => console.error('Error deleting blog slides:', error));
            inSlide.delete(id);

        } else {
            fetch(`../SlideServlet/${id}`, {
                method: 'POST'
            })
                .then(response => response.json())
                .then(result => {
                    if (result.success) {
                        alert(result.message);
                        fetchBlogs(1);
                    } else {
                        alert('推荐失败: ' + result.message);
                    }
                })
                .catch(error => console.error('Error add blog slides:', error));

            inSlide.add(id);
        }
        fetchBlogs(1);
    }
    fetchBlogs(1);
});