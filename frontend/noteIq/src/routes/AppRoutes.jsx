import { Route, Routes } from "react-router"
import SignUpPage from "../pages/SignupPage"
import LoginPage from "../pages/LoginPage"

function AppRoutes() {
    return (
        <Routes>
            {/* <Route path="/" element={<LandingPage />} /> */}
            <Route path="/login" element={<LoginPage />} />
            <Route path="/register" element={<SignUpPage />} />
            {/* <Route path="/forgot-password" element={<ForgotPasswordPage />} />
            <Route path="/reset-password" element={<ResetPasswordPage />} />
            <Route path="/dashboard" element={<ProtectedRoute> */}
                {/* <Board />
            </ProtectedRoute>} />
            <Route path="/archived" element={<ProtectedRoute>
                <Archived />
            </ProtectedRoute>} />
            <Route path="*" element={<PageNotFound />} /> */}
        </Routes>
    )
}

export default AppRoutes