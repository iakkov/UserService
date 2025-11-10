const API_BASE_URL = 'http://localhost:8080/users';

const dom = {
    usersBody: document.querySelector('#users-body'),
    listStatus: document.querySelector('#list-status'),
    getForm: document.querySelector('#get-form'),
    getId: document.querySelector('#get-id'),
    getStatus: document.querySelector('#get-status'),
    getResult: document.querySelector('#get-result'),
    createForm: document.querySelector('#create-form'),
    createName: document.querySelector('#create-name'),
    createEmail: document.querySelector('#create-email'),
    createAge: document.querySelector('#create-age'),
    createStatus: document.querySelector('#create-status'),
    updateForm: document.querySelector('#update-form'),
    updateId: document.querySelector('#update-id'),
    updateName: document.querySelector('#update-name'),
    updateEmail: document.querySelector('#update-email'),
    updateAge: document.querySelector('#update-age'),
    updateStatus: document.querySelector('#update-status'),
    deleteForm: document.querySelector('#delete-form'),
    deleteId: document.querySelector('#delete-id'),
    deleteStatus: document.querySelector('#delete-status'),
    refreshBtn: document.querySelector('#refresh-btn')
};

const defaultHeaders = {
    'Content-Type': 'application/json',
    Accept: 'application/json'
};

const setStatus = (el, message, isError = false) => {
    if (!el) {
        return;
    }
    el.style.color = isError ? '#ef4444' : '#38bdf8';
    el.textContent = message ?? '';
};

const request = async (path = '', options = {}) => {
    const response = await fetch(`${API_BASE_URL}${path}`, {
        ...options,
        headers: {
            ...defaultHeaders,
            ...(options.headers ?? {})
        }
    });

    if (!response.ok) {
        let errorMessage = `Ошибка ${response.status}`;
        try {
            const errorBody = await response.json();
            if (errorBody?.message) {
                errorMessage += `: ${errorBody.message}`;
            }
        } catch {
            // ignore parsing errors
        }
        throw new Error(errorMessage);
    }

    if (response.status === 204) {
        return null;
    }

    return response.json();
};

const renderUsers = (users = []) => {
    if (!Array.isArray(users) || !users.length) {
        dom.usersBody.innerHTML = `
            <tr>
                <td colspan="4">Нет данных. Создайте нового пользователя или обновите список.</td>
            </tr>
        `;
        return;
    }

    dom.usersBody.innerHTML = users.map((user) => `
        <tr>
            <td>${user.id}</td>
            <td>${user.name}</td>
            <td>${user.email}</td>
            <td>${user.age}</td>
        </tr>
    `).join('');
};

const refreshUsers = async () => {
    setStatus(dom.listStatus, 'Загрузка пользователей...');
    try {
        const users = await request('');
        renderUsers(users);
        setStatus(dom.listStatus, `Получено записей: ${users.length}`);
    } catch (error) {
        renderUsers([]);
        setStatus(dom.listStatus, error.message);
    }
};

const handleGetUser = async (event) => {
    event.preventDefault();
    const id = dom.getId.value.trim();
    if (!id) {
        setStatus(dom.getStatus, 'Укажите ID пользователя', true);
        return;
    }

    setStatus(dom.getStatus, 'Запрос пользователя...');
    dom.getResult.textContent = '';
    try {
        const user = await request(`/${id}`);
        dom.getResult.textContent = JSON.stringify(user, null, 2);
        setStatus(dom.getStatus, 'Готово');
    } catch (error) {
        dom.getResult.textContent = '';
        setStatus(dom.getStatus, error.message, true);
    }
};

const handleCreateUser = async (event) => {
    event.preventDefault();
    const payload = {
        name: dom.createName.value.trim(),
        email: dom.createEmail.value.trim(),
        age: Number(dom.createAge.value)
    };

    if (!payload.name || !payload.email || Number.isNaN(payload.age)) {
        setStatus(dom.createStatus, 'Заполните все поля', true);
        return;
    }

    setStatus(dom.createStatus, 'Создание пользователя...');
    try {
        const created = await request('', {
            method: 'POST',
            body: JSON.stringify(payload)
        });
        dom.createForm.reset();
        setStatus(dom.createStatus, `Пользователь создан (ID: ${created.id})`);
        await refreshUsers();
    } catch (error) {
        setStatus(dom.createStatus, error.message, true);
    }
};

const handleUpdateUser = async (event) => {
    event.preventDefault();
    const id = dom.updateId.value.trim();
    const payload = {
        name: dom.updateName.value.trim(),
        email: dom.updateEmail.value.trim(),
        age: Number(dom.updateAge.value)
    };

    if (!id || !payload.name || !payload.email || Number.isNaN(payload.age)) {
        setStatus(dom.updateStatus, 'Заполните все поля', true);
        return;
    }

    setStatus(dom.updateStatus, 'Обновление пользователя...');
    try {
        await request(`/${id}`, {
            method: 'PUT',
            body: JSON.stringify(payload)
        });
        setStatus(dom.updateStatus, 'Пользователь обновлён');
        await refreshUsers();
    } catch (error) {
        setStatus(dom.updateStatus, error.message, true);
    }
};

const handleDeleteUser = async (event) => {
    event.preventDefault();
    const id = dom.deleteId.value.trim();
    if (!id) {
        setStatus(dom.deleteStatus, 'Укажите ID пользователя', true);
        return;
    }

    setStatus(dom.deleteStatus, 'Удаление пользователя...');
    try {
        await request(`/${id}`, { method: 'DELETE' });
        dom.deleteForm.reset();
        setStatus(dom.deleteStatus, 'Пользователь удалён');
        await refreshUsers();
    } catch (error) {
        setStatus(dom.deleteStatus, error.message, true);
    }
};

dom.refreshBtn?.addEventListener('click', refreshUsers);
dom.getForm?.addEventListener('submit', handleGetUser);
dom.createForm?.addEventListener('submit', handleCreateUser);
dom.updateForm?.addEventListener('submit', handleUpdateUser);
dom.deleteForm?.addEventListener('submit', handleDeleteUser);

// Попытаться получить пользователей сразу, но ошибки не показывать пользователю
refreshUsers().catch(() => {});
