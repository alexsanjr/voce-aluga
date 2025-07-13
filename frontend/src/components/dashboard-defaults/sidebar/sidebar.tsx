import { useState } from "react";

import "./sidebar.min.css";
import { Icon } from "@iconify/react/dist/iconify.js";
import { NavLink } from "react-router-dom";
// import useAuthStore from "../../context/auth_store/auth_store";

interface SidebarItem {
    icon: string;
    label: string;
    active?: boolean;
    route: string;
}

const Sidebar: React.FC = () => {
    const [mobileMenuOpen, setMobileMenuOpen] = useState(false);

    const items: SidebarItem[] = [{ icon: "dashicons:car", label: "Frota", route: "/painel" }];

    // const { logout } = useAuthStore();

    return (
        <>
            <section className={`sidebar gradient-bg ${mobileMenuOpen ? "active" : ""}`}>
                <div className="logo-container">
                    {/* <figure>
                        <img src="../../../../src/assets/Logo.svg?react" alt="Logo G4" />
                    </figure> */}
                    <span className="logo-text">Administração</span>
                </div>

                <nav className="nav">
                    {items.map((item, index) => (
                        <NavLink
                            to={item.route}
                            end
                            className={`sidebar-item ${item.active ? "active" : ""}`}
                            key={index}
                        >
                            <i className="icon">
                                <Icon icon={item.icon} />
                            </i>
                            <span>{item.label}</span>
                        </NavLink>
                    ))}
                </nav>

                <div className="user-profile">
                    <img src="https://randomuser.me/api/portraits/men/32.jpg" alt="User" />
                    <div className="user-info">
                        <p className="user-name">Admin user</p>
                        <p className="userRole">Game Master</p>
                    </div>

                    <button
                        className="btn-logout"
                        // onClick={() => logout()}
                    >
                        <i>
                            <Icon icon="wpf:shutdown" />
                        </i>
                    </button>
                </div>
            </section>

            <div className={`overlay ${mobileMenuOpen ? "active" : ""}`} onClick={() => setMobileMenuOpen(false)} />
        </>
    );
};

export default Sidebar;
