// Service Worker placeholder to prevent 404 errors during deployment
// This file can be empty.
self.addEventListener('install', (event) => {
    // Skip waiting to activate immediately
    self.skipWaiting();
});

self.addEventListener('activate', (event) => {
    // Claim clients to start controlling immediately
    event.waitUntil(clients.claim());
});
