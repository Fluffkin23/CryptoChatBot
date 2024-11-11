// next.config.mjs
export default {
    async rewrites() {
        return [
            {
                source: '/ai/:path*',
                destination: 'http://localhost:5454/ai/:path*', // Proxy to Spring Boot backend
            },
        ];
    },
};
