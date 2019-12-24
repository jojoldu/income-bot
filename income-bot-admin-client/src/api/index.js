import axios from 'axios';

function init() {
    return axios.create({
        headers: {
            'Content-Type': 'application/json',
        },
    });
}

const instance = init();

function signup(user) {
    return instance.post('/signup', user)
}

function login(user) {
    return instance.post('/login', user)
}

function fetchBookStores(bookId) {
    return instance.get(`/api/v1/books/${bookId}`);
}

export {signup, login, fetchBookStores}