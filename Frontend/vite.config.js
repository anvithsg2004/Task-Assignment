import { defineConfig } from 'vite';
import react from '@vitejs/plugin-react';

export default defineConfig({
  plugins: [react()],
  server: {
    proxy: {
      '/api': {
        target: 'https://135.232.71.98:8085',
        changeOrigin: true,
        secure: false, // Bypasses SSL verification
        rewrite: (path) => path.replace(/^\/api/, '/api'),
      },
      '/auth': {
        target: 'https://135.232.71.98:8085',
        changeOrigin: true,
        secure: false, // Bypasses SSL verification
        rewrite: (path) => path.replace(/^\/auth/, '/auth'),
      },
    },
    port: 5173, // Match Vite’s default port
  },
});
